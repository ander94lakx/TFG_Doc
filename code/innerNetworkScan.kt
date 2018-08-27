class NetworkScanActivity : AppCompatActivity(),
        NodeListFragment.OnListFragmentInteractionListener {

    // .................

    override fun onResume() {
        super.onResume()

        if (wifiManager.wifiState == WifiManager.WIFI_STATE_ENABLED
                && wifiManager.connectionInfo.supplicantState == SupplicantState.COMPLETED) {
            if (networkScanTask.status == AsyncTask.Status.PENDING)
                networkScanTask.execute()
        } 

        // .................
    }

    // .................

    inner class NetworkScan : SequentialNetworkScan(db, wifiManager) {

        override fun onPreExecute() {
            super.onPreExecute()

            network_scan_progress_bar.max = addresses.size
            network_scan_progress_bar.progress = 0
            network_scan_progress_bar.visibility = View.VISIBLE
            setTitle(R.string.scanning)
        }

        override fun onProgressUpdate(vararg values: Node?) {
            super.onProgressUpdate(*values)

            if (currentNode is Node)
                nodeListFragment.addNode(currentNode as Node)
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)

            network_scan_progress_bar.visibility = View.GONE
            setTitle(R.string.scanned)
            ended = true
        }

        override fun updateUi() {
            network_scan_progress_bar.progress = triedHosts
        }
    }
}