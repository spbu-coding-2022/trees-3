package bst.db.models.neo4j

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

/**
 * Represents a tree entity in the Neo4j database.
 *
 * @property treeName The name of the tree.
 * @property rootNode The root node of the tree.
 * @property id The unique identifier of the tree entity.
 */
@NodeEntity
class TreeEntity(
    var treeName: String = "",
    var rootNode: TreeNodeEntity? = null
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}
