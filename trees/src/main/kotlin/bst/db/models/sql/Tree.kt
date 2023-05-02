package bst.db.models.sql

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

/**
 * Represents a tree entity in the SQL database.
 *
 * @property id The unique identifier of the tree entity.
 * @property name The name of the tree.
 * @property rootNode The root node of the tree, allowing null values.
 */
class Tree(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Tree>(Trees)

    var name by Trees.name
    var rootNode by Node optionalReferencedOn Trees.rootNode

    /**
     * Returns a string representation of the tree.
     */
    override fun toString(): String = "Node(key = $name, rootNode=$rootNode)"
}
