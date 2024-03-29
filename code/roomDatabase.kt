/**
 * A [RoomDatabase] subclass to interact with the database and their DAOs.
 */
@Database(entities = [Scan::class, Node::class, Port::class, ScanStats::class], version = 7)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun scanDao(): ScanDao

    abstract fun nodeDao(): NodeDao

    abstract fun portDao(): PortDao

    abstract fun scanStatsDao(): ScanStatsDao

    companion object {
        const val DATABASE_NAME: String = "NetScanDB"

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}
