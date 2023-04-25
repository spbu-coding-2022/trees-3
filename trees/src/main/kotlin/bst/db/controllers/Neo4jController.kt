package bst.db.controllers

import bst.RedBlackTree
import bst.db.models.neo4j.TreeEntity
import bst.db.models.neo4j.TreeNodeEntity
import bst.db.serializeClasses.SerializableNode
import bst.db.serializeClasses.SerializableTree
import bst.nodes.RBTNode
import bst.nodes.RBTNode.Color
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory
import java.util.NoSuchElementException

class Neo4jController(config: Configuration) {
    private val sessionFactory = SessionFactory(config, "bst")
    private val session = sessionFactory.openSession()


    private fun RBTNode<*, *>?.toSerializableNode(): SerializableNode? {
        if (this == null) {
            return null
        }
        return SerializableNode(key.toString(), value.toString(), 0.0, 0.0,
            color.toString(), left?.toSerializableNode(), right?.toSerializableNode())
    }

    private fun RedBlackTree<*, *>.toSerializableTree(): SerializableTree {
        return SerializableTree(treeName, rootNode?.toSerializableNode())
    }

    private fun TreeNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(key, value, x, y, metadata, left?.toSerializableNode(), right?.toSerializableNode())
    }

    private fun TreeEntity.toSerializableTree(): SerializableTree {
        return SerializableTree(treeName, rootNode?.toSerializableNode())
    }

    private fun SerializableNode.toNodeEntity(): TreeNodeEntity {
        return TreeNodeEntity(key, value, x, y, metadata, leftNode?.toNodeEntity(), rightNode?.toNodeEntity())
    }

    private fun SerializableTree.toTreeEntity(): TreeEntity {
        return TreeEntity(treeName, rootNode?.toNodeEntity())
    }

    private fun deserializeNode(node: SerializableNode?): RBTNode<Int, String>? {
        node ?: return null
        val rbtNode = RBTNode(
            key = node.key.toInt(),
            value = node.value,
            color = deserializeMetadata(node.metadata)
        )
        rbtNode.left = deserializeNode(node.leftNode)
        rbtNode.right = deserializeNode(node.rightNode)
        return rbtNode
    }

    private fun deserializeTree(tree: SerializableTree): RedBlackTree<Int, String> {
        val rbTree = RedBlackTree<Int, String>()
        rbTree.rootNode = deserializeNode(tree.rootNode)
        rbTree.treeName = tree.treeName
        return rbTree
    }

    private fun deserializeMetadata(metadata: String?): Color {
        return when (metadata) {
            "RED" -> Color.RED
            "BLACK" -> Color.BLACK
            else -> throw NoSuchFieldException("Node's color must be either RED or BLACK")
        }
    }

    fun saveTree(tree: RedBlackTree<*, *>) {
        removeTree(tree.treeName)
        val entityTree = tree.toSerializableTree().toTreeEntity()
        session.save(entityTree)
        session.query("MATCH (n: BinaryNode) WHERE NOT (n)--() DELETE (n)", mapOf<String, String>())
    }

    fun removeTree(name: String) {
        session.query(
            "MATCH (n)-[r *0..]->(m) " +
                "WHERE n.treeName = \$name DETACH DELETE m", mapOf("name" to name))
    }

    fun loadDeserialized(name: String): RedBlackTree<Int, String> {
        val tree = getTree(name)
        return deserializeTree(tree.toSerializableTree())
    }

    //  Could be useful for underlying logic of GUI
    fun loadSerialized(name: String): SerializableTree {
        val tree = getTree(name)
        return tree.toSerializableTree()
    }

    private fun getTree(name: String): TreeEntity {
        return session.queryForObject(
            TreeEntity::class.java, "MATCH (n)-[r *1..]-(m) " +
                "WHERE n.treeName = \$name RETURN n, r, m", mapOf("name" to name)) ?:
        throw NoSuchElementException("No tree with that name has been found")
    }

    fun getNames() = session.query("MATCH (n: TreeEntity) RETURN n.treeName", mapOf<String, String>()).
    flatMap { it.values.map { value -> value.toString() } }
}
