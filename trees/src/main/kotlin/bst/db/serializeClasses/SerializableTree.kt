package bst.db.serializeClasses

import kotlinx.serialization.Serializable

/**
 * Represents a serializable tree in a binary search tree structure.
 *
 * @property treeName The name of the tree.
 * @property rootNode The root node of the tree, nullable.
 */
@Serializable
class SerializableTree(
    var treeName: String,
    var rootNode: SerializableNode?
)
