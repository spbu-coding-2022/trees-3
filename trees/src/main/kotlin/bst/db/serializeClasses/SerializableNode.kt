package bst.db.serializeClasses

import kotlinx.serialization.Serializable

@Serializable
class SerializableNode(
    val key: String,
    val value: String,
    var x: Double = 0.0,
    var y: Double = 0.0,
    var metadata: String? = null,
    var leftNode: SerializableNode? = null,
    var rightNode: SerializableNode? = null
)
