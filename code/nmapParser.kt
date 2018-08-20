@Throws(XmlPullParserException::class, IOException::class)
    private fun readNmapRun(parser: XmlPullParser): NmapScan {
        var info: NmapScanInfo? = null
        val hosts = mutableListOf<NmapHost>()
        var stats: NmapRunStats? = null

        parser.require(XmlPullParser.START_TAG, namespace, "nmaprun")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG)
                continue

            when (parser.name) {
                "scaninfo" -> info = readScanInfo(parser)
                "host" -> hosts.add(readHost(parser))
                "runstats" -> stats = readRunStats(parser)
                else -> skip(parser)
            }
        }
        return NmapScan(info, hosts, stats)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readScanInfo(parser: XmlPullParser): NmapScanInfo {
        parser.require(XmlPullParser.START_TAG, namespace, "scaninfo")
        val numServices = parser.getAttributeValue(null, "numservices").toInt()
        val protocol = when (parser.getAttributeValue(null, "protocol")) {
            "ip" -> Protocol.IP
            "tcp" -> Protocol.TCP
            "udp" -> Protocol.UDP
            "sctp" -> Protocol.SCTP
            else -> Protocol.TCP // The default scan uses only TCP, and the XML attribute is required in the nmap.dtd
        }
        val services = parser.getAttributeValue(null, "services")
        parser.next()

        return NmapScanInfo(numServices, protocol, servicesStringToList(services))
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readHost(parser: XmlPullParser): NmapHost {
        var status: HostStatus? = null
        var address: Address? = null
        var hostNames = mutableListOf<HostName>()
        var ports = mutableListOf<NmapPort>()

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG)
                continue

            when (parser.name) {
                "status" -> status = readStatus(parser)
                "address" -> address = readAddress(parser)
                "hostnames" -> hostNames = readHostNames(parser)
                "ports" -> ports = readPorts(parser)
                else -> skip(parser)
            }
        }
        if (status != null && address != null)
            return NmapHost(status, address, hostNames, ports)
        else
            throw XmlPullParserException("Can't read status or address")
    }