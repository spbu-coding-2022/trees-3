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

class Neo4jController : Controller<RBTNode<Int, String>, RedBlackTree<Int, String>> {
    private val config = Configuration.Builder()
        .uri("bolt://localhost")
        .credentials("neo4j", "password")
        .build()
    private val sessionFactory = SessionFactory(config, "bst")
    private val session = sessionFactory.openSession()

    private fun RBTNode<*, *>?.toSerializableNode(): SerializableNode? {
        if (this == null) {
            return null
        }
        return SerializableNode(
            key.toString(),
            value.toString(),
            0.0,
            0.0,
            color.toString(),
            left?.toSerializableNode(),
            right?.toSerializableNode()
        )
    }

    private fun RedBlackTree<*, *>.toSerializableTree(treeName: String): SerializableTree {
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
        return rbTree
    }

    private fun deserializeMetadata(metadata: String?): Color {
        return when (metadata) {
            "RED" -> Color.RED
            "BLACK" -> Color.BLACK
            else -> throw NoSuchFieldException("Node's color must be either RED or BLACK")
        }
    }

    override fun saveTree(tree: RedBlackTree<Int, String>, treeName: String) {
        removeTree(treeName)
        val entityTree = tree.toSerializableTree(treeName).toTreeEntity()
        entityTree.treeName = treeName
        session.save(entityTree)
        session.query("MATCH (n: BinaryNode) WHERE NOT (n)--() DELETE (n)",
            mapOf<String, String>())
    }

    override fun removeTree(treeName: String) {
        session.query(
            "MATCH (n)-[r *0..]->(m) " + "WHERE n.treeName = \$treeName DETACH DELETE m",
            mapOf("treeName" to treeName)
        )
    }

    override fun getTree(treeName: String): RedBlackTree<Int, String>? {
        val tree = loadTree(treeName)
        return tree?.let { deserializeTree(it.toSerializableTree()) }
    }

    private fun loadTree(treeName: String): TreeEntity? {
        return session.queryForObject(
            TreeEntity::class.java,
            "MATCH (n)-[r *1..]-(m) " + "WHERE n.treeName = \$treeName RETURN n, r, m",
            mapOf("treeName" to treeName)
        ) ?: null
    }

    fun close() {
        session.clear()
        sessionFactory.close()
    }

    fun getNames() = session.query("MATCH (n: TreeEntity) RETURN n.treeName", mapOf<String, String>())
        .flatMap { it.values.map { value -> value.toString() } }
}
