abstract class NetworkScan(protected val db: AppDatabase, private val wifiManager: WifiManager) 
                    : AsyncTask<Unit, Node, Unit>() {

// ...................

    override fun onPreExecute() {
        val ip = NmapRunner.intToIp(wifiManager.connectionInfo.ipAddress)
        val netmask = NmapRunner.intToIp(wifiManager.dhcpInfo.netmask)

        scanName = wifiManager.connectionInfo.ssid.trim('"')
        addresses = SubnetUtils(ip, netmask).info.allAddresses

        scanStartTimestamp = System.nanoTime()
    }

    override fun onProgressUpdate(vararg values: Node?) {
        if (emptyScan) {
            db.scanDao().insertScan(Scan(scanName))
            scanId = db.scanDao().lastInsertedId()
            emptyScan = false
        }

        for (scan in values)
            if (scan != null)
                hostsUp++
    }

    override fun onPostExecute(result: Unit?) {
        val scanTimeInSeconds = ((System.nanoTime() - scanStartTimestamp!!) / Math.pow(10.0, 9.0)).toFloat()
        db.scanStatsDao().insertScanStats(ScanStats(scanId, addresses.size, hostsUp, addresses.size - hostsUp, scanTimeInSeconds))
    }

// ...................

}