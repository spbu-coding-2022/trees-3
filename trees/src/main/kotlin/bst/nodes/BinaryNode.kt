package bst.nodes
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.PostLoad
import org.neo4j.ogm.annotation.Property

<<<<<<< HEAD
@NodeEntity
abstract class BinaryNode<K: Comparable<K>, V, Self: BinaryNode<K, V, Self>>(
    var key: K,
    var value: V
) {
<<<<<<< HEAD
    var x: Double = 0.0
    var y: Double = 0.0
    var left: Self? = null
    var right: Self? = null

}
=======
    @Id
    @GeneratedValue
    var id: Long? = null
    var left: Self? = null
    var right: Self? = null

    @Property(name = "key")
    private val clavis = key as Any

    @PostLoad
    fun keyInit() {
        key = clavis as K
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BinaryNode<*, *, *>

        if (id != other.id) return false
        if (key != other.key) return false

        return true
    }

}
<<<<<<< HEAD
>>>>>>> 2dd00ac (db: initial commit for neo4j)
=======
=======
abstract class BinaryNode<K: Comparable<K>, V, Self: BinaryNode<K, V, Self>>(
    var key: K,
    var value: V
) {
    var x: Double = 0.0
    var y: Double = 0.0
    var left: Self? = null
    var right: Self? = null

}
>>>>>>> 9edffc9 (Add x, y coordinates to nodes)
>>>>>>> 001bf13 (Add x, y coordinates to nodes)
