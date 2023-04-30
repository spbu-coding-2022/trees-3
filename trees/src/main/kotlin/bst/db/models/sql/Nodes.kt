package bst.db.models.sql

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Nodes : IntIdTable("nodes") {
    val key = varchar("key", length = 256).uniqueIndex()
    val value = varchar("value", length = 256)
    val x = double("x_coordinate").default(0.0)
    val y = double("y_coordinate").default(0.0)
    val left = reference("left", Nodes).nullable()
    val right = reference("right", Nodes).nullable()
    val tree = reference("tree", Trees, onDelete = ReferenceOption.CASCADE)
}
