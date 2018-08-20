fun runScan(hosts: List<String>): NmapScan? {

    if(!NmapInstaller.installed)
        throw Exception("Nmap is not installed in the device")

    startProcess()
    var outputFile = setupOutputFile()

    processOutputStream?.writeBytes(commandBuilder(hosts, outputFile))
    processOutputStream?.writeBytes("exit\n")
    processOutputStream?.flush()

    // Gets all the output from the process
    var pstdout: String? = processInputReader?.readLine()
    val wholeOutput = mutableListOf<String>()
    while (pstdout != null) {
        pstdout += "\n"
        wholeOutput.add(pstdout)
        pstdout = processInputReader?.readLine()
    }

    scanProcess?.waitFor()
    outputFile = File(outputFile.path)

    val scan = parser.parse(outputFile.inputStream())

    outputFile.delete()

    endProcess()

    return scan
}