package db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Tree(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<Tree>(Trees)
    var name by Trees.name
    var rootNode by Node optionalReferencedOn Trees.rootNode
    override fun toString(): String = "Node(key = $name, rootNode=$rootNode)"
}

//fun createTree
