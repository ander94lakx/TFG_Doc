protected fun getMacAddress(ip: String): String? {
    val reader = BufferedReader(InputStreamReader(FileInputStream(ARP_TABLE), "UTF-8"))
    reader.use {
        var line = it.readLine()

        while (line != null) {
            val arpLine = line.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }

            val arpIp = arpLine[0]
            val flag = arpLine[2]
            val macAddress = arpLine[3]

            if (arpIp == ip)
                if (flag != ARP_INCOMPLETE && macAddress != ARP_INACTIVE)
                    return macAddress.toUpperCase()

            line = it.readLine()
        }
    }
    return null
}