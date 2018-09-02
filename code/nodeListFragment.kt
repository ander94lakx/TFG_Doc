class NodeListFragment : Fragment() {

    //............

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            scanId = (arguments as Bundle).getInt(ARG_SCAN_ID)
            scanName = (arguments as Bundle).getString(ARG_SCAN_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_node_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val db: AppDatabase = AppDatabase.getInstance(view.context)
            val nodes =
                    if (scanId != null)
                        db.nodeDao().getNodesFromScan(scanId!!)
                    else
                        listOf()
            nodeRecyclerViewAdapter = MyNodeRecyclerViewAdapter(nodes.toMutableList(), listener)
            view.adapter = nodeRecyclerViewAdapter
        }

        return view
    }

    //............
}