package bst.db.models.neo4j

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity
class TreeEntity(
    var treeName: String = "",
    var rootNode: TreeNodeEntity? = null
) {
    @Id
    @GeneratedValue
    var id: Long? = null
}
