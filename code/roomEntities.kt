/**
 * A single node in a network scan.
 */
@Entity
open class Scan(val name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var date: Date = Date()
}

@Entity(foreignKeys = [ForeignKey(entity = Scan::class, parentColumns = ["id"], childColumns = ["scanId"])])
open class ScanStats(@PrimaryKey
                     var scanId: Int,
                     var scannedHosts: Int,
                     var hostsUp: Int,
                     var hostsDown: Int,
                     var scanTime: Float)

/**
 * A single node in a network scan.
 */
@Entity
open class Node(val name: String,
                val ip: String,
                val mac: String,
                val vendor: String,
                val timeElapsed: Float,
                val scanId: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

/**
 * A single port of a concrete node.
 */
@Entity(primaryKeys = ["id", "nodeId"])
open class Port(val id: Int,
                val nodeId: Int,
                val protocol: Protocol,
                val service: String,
                val state: StateType,
                val reason: String)