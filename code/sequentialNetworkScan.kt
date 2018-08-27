abstract class SequentialNetworkScan(db: AppDatabase, wifiManager: WifiManager) : NetworkScan(db, wifiManager) {

    override fun doInBackground(vararg __nothing: Unit) {
        val nmapRunner = NmapRunner(ScanType.REGULAR)
        for (address in addresses) {
            val inetAddress = InetAddress.getByName(address)
            val reachable = inetAddress.isReachable(pingTimeout)

            if (reachable) {
                val singleHostScan = nmapRunner.runScan(listOf(address))

                if (!isCancelled) nmapRunner.scanProcess?.waitFor()

                if (singleHostScan != null
                        && singleHostScan.hosts.isNotEmpty()
                        && singleHostScan.hosts[0].status.state == HostStates.UP) {

                    val ip = singleHostScan.hosts[0].address.address
                    val mac: String = getMacAddress(ip) ?: ""
                    val name =
                            if (singleHostScan.hosts[0].hostNames.isNotEmpty())
                                singleHostScan.hosts[0].hostNames[0].name
                            else {
                                if(!OUIs.downloaded)
                                    OUIs.waitForDownload()

                                if (OUIs.checkVendorFromMac(mac) == "")
                                    ip
                                else
                                    OUIs.checkVendorFromMac(mac)
                            }
                    val vendor: String =
                            if (!OUIs.downloaded)
                                OUIs.checkVendorFromMac(mac)
                            else
                                ""

                    val timeElapsed: Float =
                            if (singleHostScan.runStats != null)
                                singleHostScan.runStats.timeElapsed
                            else
                                -1.0f
                    val scanId = db.scanDao().lastInsertedId()

                    currentNode = Node(name, ip, mac, vendor, timeElapsed, scanId)
                    db.nodeDao().insertNode(currentNode as Node)

                    for (nmapPort in singleHostScan.hosts[0].ports) {
                        val port = Port(nmapPort.id,
                                db.nodeDao().lastInsertedId(),
                                nmapPort.type,
                                nmapPort.service,
                                nmapPort.state.state,
                                nmapPort.state.reason)
                        db.portDao().insertPort(port)
                    }

                    publishProgress(currentNode)

                } else {
                    currentNode = null
                }
            }
            triedHosts++
            updateUi()
        }
    }
}