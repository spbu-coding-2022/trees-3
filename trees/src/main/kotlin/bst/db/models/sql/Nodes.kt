package bst.db.models.sql

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * Represents the database table for storing node entities in a binary search tree.
 *
 * @property key Column for the key of the node.
 * @property value Column for the value associated with the node.
 * @property x Column for the x-coordinate of the node in a visualization, with a default value of 0.0.
 * @property y Column for the y-coordinate of the node in a visualization, with a default value of 0.0.
 * @property left Column for the left child node reference, allowing null values.
 * @property right Column for the right child node reference, allowing null values.
 * @property tree Column for the tree reference to which the node belongs, with CASCADE delete behavior.
 */
object Nodes : IntIdTable("nodes") {
    val key = varchar("key", length = 256)
    val value = varchar("value", length = 256)
    val x = double("x_coordinate").default(0.0)
    val y = double("y_coordinate").default(0.0)
    val left = reference("left", Nodes).nullable()
    val right = reference("right", Nodes).nullable()
    val tree = reference("tree", Trees, onDelete = ReferenceOption.CASCADE)
}
