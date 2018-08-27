class MyAsyncTask() : AsyncTask<Params, Progress, Result>() {

    override fun onPreExecute() { /* ... */ }

    override fun doInBackground(varagr params: Param) { /* ... */ }

    override fun onProgressUpdate(progress: Progress) { /* ... */ }

    override fun onPostExecute(result: Result) { /* ... */ }
}