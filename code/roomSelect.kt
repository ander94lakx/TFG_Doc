override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.fragment_node_list, container, false)

    // Set the adapter
    if (view is RecyclerView) {
        val db: AppDatabase = AppDatabase.getInstance(view.context)
        val nodes = db.nodeDao().getNodesFromScan(scanId)
        nodeRecyclerViewAdapter = MyNodeRecyclerViewAdapter(nodes.toMutableList(), listener)
        view.adapter = nodeRecyclerViewAdapter
    }

    return view
}