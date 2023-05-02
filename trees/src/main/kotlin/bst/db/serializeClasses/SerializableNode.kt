package bst.db.serializeClasses

import kotlinx.serialization.Serializable

/**
 * Represents a serializable node in a binary search tree.
 *
 * @property key The key of the node.
 * @property value The value associated with the node.
 * @property x The x-coordinate of the node in a visualization, with a default value of 0.0.
 * @property y The y-coordinate of the node in a visualization, with a default value of 0.0.
 * @property metadata Additional metadata associated with the node, nullable.
 * @property leftNode The left child node, nullable.
 * @property rightNode The right child node, nullable.
 */
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
