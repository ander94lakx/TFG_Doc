private fun startProcess() {
    if (scanProcess == null || processInputReader == null || processOutputStream == null) {
        // Creates a new process that runs a shell
        val processBuilder = ProcessBuilder("sh")
        processBuilder.redirectErrorStream(true)
        scanProcess = processBuilder.start()

        // Creates a couple of streams to redirect the IO
        processOutputStream = DataOutputStream(scanProcess?.outputStream)
        processInputReader = BufferedReader(InputStreamReader(scanProcess?.inputStream))
    }
}