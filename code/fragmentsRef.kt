class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        ShareFragment.OnFragmentInteractionListener,
        ScanListFragment.OnListFragmentInteractionListener,
        ScanDirectionFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener {

    private var scanListFragment = ScanListFragment()
    private val scanDirectionFragment = ScanDirectionFragment()
    private val shareFragment = ShareFragment()
    private val aboutFragment = AboutFragment()

    // ...............

}