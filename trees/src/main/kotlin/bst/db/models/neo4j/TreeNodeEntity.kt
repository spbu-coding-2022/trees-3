package bst.db.models.neo4j

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

/**
 * Represents a node entity in a binary search tree stored in the Neo4j database.
 *
 * @property key The key of the node.
 * @property value The value associated with the node.
 * @property x The x-coordinate of the node in a visualization (default is 0.0).
 * @property y The y-coordinate of the node in a visualization (default is 0.0).
 * @property metadata Additional metadata associated with the node.
 * @property left The left child node of the current node.
 * @property right The right child node of the current node.
 * @property id The unique identifier of the node entity.
 */
@NodeEntity
class TreeNodeEntity(
    val key: String,
    val value: String,
    val x: Double = 0.0,
    val y: Double = 0.0,
    var metadata: String? = null,
    @Relationship(type = "LEFT") var left: TreeNodeEntity? = null,
    @Relationship(type = "RIGHT") var right: TreeNodeEntity? = null
) {
    /**
     * The unique identifier of the node entity.
     */
    @Id
    @GeneratedValue
    var id: Long? = null
}
