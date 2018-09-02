override fun onNavigationItemSelected(item: MenuItem): Boolean {
    val transaction = supportFragmentManager.beginTransaction()
    netscan_fab.visibility = View.GONE
    when (item.itemId) {
        R.id.nav_my_scans -> {
            transaction.replace(R.id.content_main, scanListFragment).commit()
            netscan_fab.visibility = View.VISIBLE
        }
        R.id.nav_scan_direction -> {
            transaction.replace(R.id.content_main, scanDirectionFragment).commit()
        }
        R.id.nav_share -> {
            transaction.replace(R.id.content_main, shareFragment).commit()
        }
        R.id.nav_about -> {
            transaction.replace(R.id.content_main, aboutFragment).commit()
        }
    }

    drawer_layout.closeDrawer(GravityCompat.START)
    item.isChecked = true
    return true
}