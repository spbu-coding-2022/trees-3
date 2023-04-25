package bst.db.models

import bst.db.models.Nodes
import org.jetbrains.exposed.dao.id.IntIdTable

object Trees : IntIdTable("trees") {
    val name = varchar("name", length = 128).uniqueIndex()
    val rootNode = reference("rootNode", Nodes.id).nullable()
}

