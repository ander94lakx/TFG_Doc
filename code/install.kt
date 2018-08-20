fun install(activity: Activity, force: Boolean = false): File {

    val context = activity.applicationContext
    nmapPath = context.filesDir.path
    nmapBinPath = "$nmapPath/nmap/bin/nmap"

    if (!nmapDirExists() || !installed || force) {
        val assetManager = activity.assets
        val ins = assetManager.open(filePrefix + Build.SUPPORTED_ABIS[0] + fileSuffix)
        val zin = ZipInputStream(ins)
        try {
            var entry: ZipEntry = zin.nextEntry
            do {
                if (entry.isDirectory) {
                    val myDir = File("$nmapPath/${entry.name}")

                    if (!myDir.isDirectory)
                        if (!myDir.mkdirs())
                            throw IOException("Cannot create the directory")

                } else {
                    val buffer = ByteArray(2048)
                    val outStream = FileOutputStream("$nmapPath/${entry.name}")
                    val bufferOut = BufferedOutputStream(outStream, buffer.size)

                    var size = zin.read(buffer, 0, buffer.size)
                    while (size != -1) {
                        bufferOut.write(buffer, 0, size)
                        size = zin.read(buffer, 0, buffer.size)
                    }

                    bufferOut.flush()
                    bufferOut.close()
                }
                entry = zin.nextEntry
            } while (entry != null)
        } catch (e: Exception) {
            Log.e("Nmap unzipping...", e.message)
        }
        zin.close()
        nseDbUpdate()
    }

    val nmapExec = File(nmapBinPath)
    nmapExec.setExecutable(true)

    installed = true

    return nmapExec
}