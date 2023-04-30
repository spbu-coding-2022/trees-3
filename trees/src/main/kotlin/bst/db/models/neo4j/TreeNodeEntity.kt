package bst.db.models.neo4j

import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

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
    @Id
    @GeneratedValue
    var id: Long? = null
}
