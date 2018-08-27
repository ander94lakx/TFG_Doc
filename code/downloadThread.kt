override val downloadThread = Thread(Runnable {
    val url = URL(urlString)
    val urlConnection = url.openConnection()
    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.getInputStream()))
    val urlDataText = bufferedReader.readLines()
    val commentsRemoved = urlDataText.filter { it.isNotEmpty() && it[0] != '#' }
    for (line in commentsRemoved) {
        val data: List<String> = line.split('\t')
        if (data.size == 3) {
            vendorList.add(Vendor(data[0], data[1], data[2]))
        }
    }
    bufferedReader.close()
    downloaded = true
})