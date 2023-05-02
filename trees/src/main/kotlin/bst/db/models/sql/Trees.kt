package bst.db.models.sql

import org.jetbrains.exposed.dao.id.IntIdTable

/**
 * Represents the database table for storing tree entities in the SQL database.
 *
 * @property name Column for the name of the tree.
 * @property rootNode Column for the root node reference of the tree, allowing null values.
 */
object Trees : IntIdTable("trees") {
    val name = varchar("name", length = 128).uniqueIndex()
    val rootNode = reference("rootNode", Nodes.id).nullable()
}
