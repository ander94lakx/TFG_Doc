object OUIs: DownloadableResource() {

    override val urlString: String = "https://code.wireshark.org/review/gitweb?p=wireshark.git;a=blob_plain;f=manuf"

    private data class Vendor(val mac: String, val vendorShort: String, val vendorFull: String)
    private val vendorList: MutableList<Vendor> = mutableListOf()

    override var downloaded = false

    override val downloadThread = Thread(Runnable {/* ... */})

    override fun downloadFile()  {/* ... */}

    override fun waitForDownload() {/* ... */}

    fun checkVendorFromMac(mac: String): String {
        if (mac == "")
            return ""

        val macPrefix = mac.substring(0, 8)
        for (vendor in vendorList)
            if (vendor.mac == macPrefix)
                return vendor.vendorFull

        return ""
    }
}