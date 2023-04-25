package bst.db.serializeClasses

import bst.db.serializeClasses.SerializableNode
import kotlinx.serialization.Serializable

@Serializable
class SerializableTree (
    var treeName: String,
    var rootNode: SerializableNode?,
    )
