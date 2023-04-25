package bst.db.models.sql

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID

class Node(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Node>(Nodes)

    var key by Nodes.key
    var value by Nodes.value
    var x by Nodes.x
    var y by Nodes.y
    var left by Node optionalReferencedOn Nodes.left
    var right by Node optionalReferencedOn Nodes.right
    var tree by Tree referencedOn Nodes.tree
    override fun toString(): String = "Node(key = $key, value=$value, x=$x, y=$y, left=$left, right=$right, tree=$tree)"

}
