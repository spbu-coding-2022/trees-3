package bst.db

import kotlinx.serialization.Serializable

@Serializable
class SerializableTree (
    var treeName: String,
    var rootNode: SerializableNode?,
    )
