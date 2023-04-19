package db

import db.Nodes
import org.jetbrains.exposed.dao.id.IntIdTable

object Trees : IntIdTable("trees") {
    val name = varchar("name", length = 128)
    val rootNode = reference("rootNode", Nodes).nullable()
}

