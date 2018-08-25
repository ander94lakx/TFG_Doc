override fun onProgressUpdate(vararg values: Node?) {
    if (emptyScan) {
        db.scanDao().insertScan(Scan(scanName))
        scanId = db.scanDao().lastInsertedId()
        emptyScan = false
    }
}