private fun commandBuilder(hosts: List<String>, outputFile: File): String {
    var hostsString = " "
    hosts.forEach { hostsString += "$it " }
    val args = when (scanType) {
        ScanType.REGULAR -> ""
        ScanType.PING -> "-sn"
        ScanType.QUICK -> "-T4"
        ScanType.FULL -> "-A --no-stylesheet"
    }
    return "${NmapInstaller.nmapBinPath} $args $hostsString -oX ${outputFile.path}\n"
}