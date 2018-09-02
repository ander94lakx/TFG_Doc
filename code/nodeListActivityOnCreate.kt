class NodeListActivity : AppCompatActivity(), NodeListFragment.OnListFragmentInteractionListener {

    // ............

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_node_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.extras != null) {
            scanId = intent.extras.getInt("scan_id")
            scanName = intent.extras.getString("scan_name")

            getPreferences(Context.MODE_PRIVATE).edit().putInt("scan_id", scanId).apply()
            getPreferences(Context.MODE_PRIVATE).edit().putString("scan_name", scanName).apply()
        } 
        
        title = scanName
        nodeListFragment = NodeListFragment.newInstance(scanId, scanName)
        supportFragmentManager.beginTransaction().add(R.id.content_node_list, nodeListFragment).commit()
    }

    //............
}