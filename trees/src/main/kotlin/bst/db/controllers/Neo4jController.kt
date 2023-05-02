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

/**
 * Controller class for managing Red-Black Trees stored in a Neo4j database.
 */
class Neo4jController : Controller<RBTNode<Int, String>, RedBlackTree<Int, String>> {
    /**
     * The Neo4j database configuration.
     */
    private val config = Configuration.Builder()
        .uri("bolt://localhost")
        .credentials("neo4j", "password")
        .build()

    /**
     * The session factory for creating database sessions.
     */
    private val sessionFactory = SessionFactory(config, "bst")

    /**
     * The database session.
     */
    private val session = sessionFactory.openSession()

    /**
     * Converts an RBTNode object to a SerializableNode object.
     *
     * @return the SerializableNode representation of the RBTNode
     */
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

    /**
     * Converts a RedBlackTree object to a SerializableTree object.
     *
     * @param treeName the name of the tree
     * @return the SerializableTree representation of the RedBlackTree
     */
    private fun RedBlackTree<*, *>.toSerializableTree(treeName: String): SerializableTree {
        return SerializableTree(treeName, rootNode?.toSerializableNode())
    }

    /**
     * Converts a TreeNodeEntity object to a SerializableNode object.
     *
     * @return the SerializableNode representation of the TreeNodeEntity
     */
    private fun TreeNodeEntity.toSerializableNode(): SerializableNode {
        return SerializableNode(key, value, x, y, metadata, left?.toSerializableNode(), right?.toSerializableNode())
    }

    /**
     * Converts a TreeEntity object to a SerializableTree object.
     *
     * @return the SerializableTree representation of the TreeEntity
     */
    private fun TreeEntity.toSerializableTree(): SerializableTree {
        return SerializableTree(treeName, rootNode?.toSerializableNode())
    }

    /**
     * Converts a SerializableNode object to a TreeNodeEntity object.
     *
     * @return the TreeNodeEntity representation of the SerializableNode
     */
    private fun SerializableNode.toNodeEntity(): TreeNodeEntity {
        return TreeNodeEntity(key, value, x, y, metadata, leftNode?.toNodeEntity(), rightNode?.toNodeEntity())
    }

    /**
     * Converts a SerializableTree object to a TreeEntity object.
     *
     * @return the TreeEntity representation of the SerializableTree
     */
    private fun SerializableTree.toTreeEntity(): TreeEntity {
        return TreeEntity(treeName, rootNode?.toNodeEntity())
    }

    /**
     * Deserializes a SerializableNode object to an RBTNode object.
     *
     * @return the deserialized RBTNode object
     */
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

    /**
     * Deserializes a SerializableTree object to a RedBlackTree object.
     *
     * @return the deserialized RedBlackTree object
     */
    private fun deserializeTree(tree: SerializableTree): RedBlackTree<Int, String> {
        val rbTree = RedBlackTree<Int, String>()
        rbTree.rootNode = deserializeNode(tree.rootNode)
        return rbTree
    }

    /**
     * Deserializes a metadata string to a Color enum.
     *
     * @return the deserialized Color enum
     * @throws NoSuchFieldException if the metadata does not match any Color values
     */
    private fun deserializeMetadata(metadata: String?): Color {
        return when (metadata) {
            "RED" -> Color.RED
            "BLACK" -> Color.BLACK
            else -> throw NoSuchFieldException("Node's color must be either RED or BLACK")
        }
    }

    /**
     * Saves a RedBlackTree to the Neo4j database with the specified name.
     *
     * @param tree     the RedBlackTree to save
     * @param treeName the name of the tree
     */
    override fun saveTree(tree: RedBlackTree<Int, String>, treeName: String) {
        removeTree(treeName)
        val entityTree = tree.toSerializableTree(treeName).toTreeEntity()
        entityTree.treeName = treeName
        session.save(entityTree)
        session.query(
            "MATCH (n: BinaryNode) WHERE NOT (n)--() DELETE (n)",
            mapOf<String, String>()
        )
    }

    /**
     * Removes a RedBlackTree with the specified name from the Neo4j database.
     *
     * @param treeName the name of the tree to remove
     */
    override fun removeTree(treeName: String) {
        session.query(
            "MATCH (n)-[r *0..]->(m) " + "WHERE n.treeName = \$treeName DETACH DELETE m",
            mapOf("treeName" to treeName)
        )
    }

    /**
     * Retrieves a RedBlackTree with the specified name from the Neo4j database.
     *
     * @param treeName the name of the tree to retrieve
     * @return the retrieved RedBlackTree object, or null if the tree is not found
     */
    override fun getTree(treeName: String): RedBlackTree<Int, String>? {
        val tree = loadTree(treeName)
        return tree?.let { deserializeTree(it.toSerializableTree()) }
    }

    /**
     * Loads a TreeEntity from the Neo4j database with the specified name.
     *
     * @param treeName the name of the tree to load
     * @return the loaded TreeEntity object, or null if the tree is not found
     */
    private fun loadTree(treeName: String): TreeEntity? {
        return session.queryForObject(
            TreeEntity::class.java,
            "MATCH (n)-[r *1..]-(m) " + "WHERE n.treeName = \$treeName RETURN n, r, m",
            mapOf("treeName" to treeName)
        ) ?: null
    }

    /**
     * Closes the Neo4j session and session factory.
     */
    fun close() {
        session.clear()
        sessionFactory.close()
    }

    /**
     * Retrieves the names of all the trees stored in the Neo4j database.
     *
     * @return a list of tree names
     */
    fun getNames() = session.query("MATCH (n: TreeEntity) RETURN n.treeName", mapOf<String, String>())
        .flatMap { it.values.map { value -> value.toString() } }
}
