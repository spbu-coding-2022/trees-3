package db
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Nodes : IntIdTable("nodes") {
    val key = varchar("key", length = 256).uniqueIndex()
    val value = varchar("value", length = 256)
    val x = double("x_coordinate").nullable()
    val y = double("y_coordinate").nullable()
    val left = reference("left", Nodes).nullable()
    val right = reference("right", Nodes).nullable()
    val tree = reference("tree", Trees, onDelete = ReferenceOption.CASCADE)
}

