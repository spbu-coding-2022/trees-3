package bst.db.models.sql

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a node entity in a binary search tree stored in the SQL database.
 *
 * @property id The unique identifier of the node entity.
 * @property key The key of the node.
 * @property value The value associated with the node.
 * @property x The x-coordinate of the node in a visualization.
 * @property y The y-coordinate of the node in a visualization.
 * @property left The left child node of the current node.
 * @property right The right child node of the current node.
 * @property tree The tree to which the node belongs.
 */
class Node(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Node>(Nodes)

    var key by Nodes.key
    var value by Nodes.value
    var x by Nodes.x
    var y by Nodes.y
    var left by Node optionalReferencedOn Nodes.left
    var right by Node optionalReferencedOn Nodes.right
    var tree by Tree referencedOn Nodes.tree

    /**
     * Returns a string representation of the node.
     */
    override fun toString(): String = "Node(key = $key, value=$value, x=$x, y=$y, left=$left, right=$right, tree=$tree)"
}
