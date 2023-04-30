package bst.db.serializeClasses

import kotlinx.serialization.Serializable

@Serializable
class SerializableTree(
    var treeName: String,
    var rootNode: SerializableNode?
)
