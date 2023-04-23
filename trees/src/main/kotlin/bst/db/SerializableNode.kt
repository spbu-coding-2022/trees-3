package bst.db
import kotlinx.serialization.*

@Serializable
class SerializableNode(
    val key: String,
    val value : String,
    val x : Double = 0.0,
    val y: Double = 0.0,
    var leftNode: SerializableNode? = null,
    var rightNode: SerializableNode? = null,
)
