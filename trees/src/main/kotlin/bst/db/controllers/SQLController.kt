package bst.db.controllers

import bst.BSTree
import bst.db.models.sql.Node
import bst.db.models.sql.Nodes
import bst.db.models.sql.Tree
import bst.db.models.sql.Trees
import bst.db.serializeClasses.SerializableNode
import bst.db.serializeClasses.SerializableTree
import bst.nodes.BSTNode
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * A controller class for interacting with a binary search tree (BST) stored in an SQL database.
 * It provides methods for saving, retrieving, and removing BSTs from the database.
 */
class SQLController : Controller<BSTNode<Int, String>, BSTree<Int, String>> {
    /**
     * Connects to the SQL database and creates the required tables if they don't exist.
     */
    private fun connectDB() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/test",
            driver = "org.postgresql.Driver",
            user = "test",
            password = "test-test"
        )
        createTables()
    }

    /**
     * Removes a tree from the database with the specified name.
     *
     * @param treeName the name of the tree to remove
     */
    override fun removeTree(treeName: String) {
        transaction {
            try {
                Tree.find { (Trees.name eq treeName) }
                    .firstOrNull()?.delete()
            } catch (e: ExposedSQLException) {
                println("Tree does not exists")
            }
        }
    }

    /**
     * Serializes a BSTNode object into a SerializableNode object.
     *
     * @param node the BSTNode to serialize
     * @return the serialized SerializableNode object
     */
    private fun serializeNode(node: BSTNode<Int, String>?): SerializableNode? {
        return if (node == null) {
            null
        } else {
            val serializableNode = SerializableNode(
                key = node.key.toString(),
                value = node.value,
                leftNode = null,
                rightNode = null
            )
            serializableNode.rightNode = serializeNode(node.right)
            serializableNode.leftNode = serializeNode(node.left)
            serializableNode
        }
    }

    /**
     * Serializes a BSTree object into a SerializableTree object.
     *
     * @param tree the BSTree to serialize
     * @param treeName the name of the tree
     * @return the serialized SerializableTree object
     */
    private fun serializeTree(tree: BSTree<Int, String>, treeName: String): SerializableTree? {
        return tree.rootNode?.let { serializeNode(it) }
            ?.let { SerializableTree(treeName = treeName, rootNode = it) }
    }

    /**
     * Creates the required tables in the database if they don't exist.
     */
    private fun createTables() {
        transaction {
            SchemaUtils.create(Trees)
            SchemaUtils.create(Nodes)
        }
    }

    /**
     * Converts a SerializableNode object into a Node object and associates it with the specified Tree object.
     *
     * @param treeDao the Tree object to associate the Node with
     * @return the created Node object
     */
    private fun SerializableNode.toNodeDao(treeDao: Tree): Node {
        return Node.new {
            key = this@toNodeDao.key
            value = this@toNodeDao.value
            x = this@toNodeDao.x
            y = this@toNodeDao.y
            left = this@toNodeDao.leftNode?.toNodeDao(treeDao)
            right = this@toNodeDao.rightNode?.toNodeDao(treeDao)
            tree = treeDao
        }
    }

    /**
     * Saves a BSTree object to the database with the specified name.
     *
     * @param tree the BSTree to save
     * @param treeName the name of the tree
     */
    override fun saveTree(tree: BSTree<Int, String>, treeName: String) {
        connectDB()
        removeTree(treeName)
        val serializedTree = serializeTree(tree, treeName)
        transaction {
            addLogger(StdOutSqlLogger)
            createTables()
            val daoTree = Tree.new {
                if (serializedTree != null) name = serializedTree.treeName
            }
            daoTree.rootNode = serializedTree?.rootNode?.toNodeDao(daoTree)
        }
    }

    /**
     * Converts a Node object into a SerializableNode object.
     *
     * @param treeDao the associated Tree object
     * @return the serialized SerializableNode object
     */
    private fun Node.getSerializedNode(treeDao: Tree): SerializableNode {
        return SerializableNode(
            this@getSerializedNode.key,
            this@getSerializedNode.value,
            this@getSerializedNode.x,
            this@getSerializedNode.y,
            null,
            this@getSerializedNode.left?.getSerializedNode(treeDao),
            this@getSerializedNode.right?.getSerializedNode(treeDao)
        )
    }

    /**
     * Finds a SerializableTree object with the specified name in the database.
     *
     * @param treeName the name of the tree to find
     * @return the found SerializableTree object, or null if not found
     */
    private fun findTree(treeName: String): SerializableTree? {
        connectDB()
        val treeDAO = Tree.find { Trees.name eq treeName }.firstOrNull() ?: return null
        return treeDAO.rootNode?.getSerializedNode(treeDAO)?.let {
            SerializableTree(
                treeName,
                it
            )
        }
    }

    /**
     * Checks if a given string is numeric.
     *
     * @param s the string to check
     * @return true if the string is numeric, false otherwise
     */
    private fun isNumeric(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * Deserializes a SerializableNode object with integer keys into a BSTNode object.
     *
     * @param node the SerializableNode to deserialize
     * @return the deserialized BSTNode object
     */
    private fun deserializeNodeDoubleKey(node: SerializableNode?): BSTNode<Int, String>? {
        return if (node == null) {
            null
        } else {
            val deserializableNode = BSTNode(key = node.key.toInt(), value = node.value)
            deserializableNode.right = deserializeNodeDoubleKey(node.rightNode)
            deserializableNode.left = deserializeNodeDoubleKey(node.leftNode)
            deserializableNode
        }
    }

    /**
     * Deserializes a SerializableTree object into a BSTree object.
     * The deserialization process converts the SerializableTree's root node
     * and its children SerializableNodes into BSTNodes.
     *
     * @param tree the SerializableTree to deserialize
     * @return the deserialized BSTree object, or null if the serialization is invalid
     */
    private fun deserializeTree(tree: SerializableTree?): BSTree<Int, String>? {
        if (tree != null) {
            if (isNumeric(tree.rootNode!!.key)) {
                val rootNode = deserializeNodeDoubleKey(tree.rootNode)
                val deserializedTree: BSTree<Int, String> = BSTree(rootNode?.key, rootNode?.value)
                deserializedTree.rootNode = rootNode
                return deserializedTree
            }
        }
        return null
    }

    /**
     * Retrieves a BSTree object from the database with the specified name.
     * The retrieval process involves finding the SerializableTree with the given name,
     * deserializing it, and converting it into a BSTree.
     *
     * @param treeName the name of the tree to retrieve
     * @return the retrieved BSTree object, or null if the tree is not found in the database
     */
    override fun getTree(treeName: String): BSTree<Int, String>? {
        var deserializedTree: SerializableTree? = null
        transaction {
            deserializedTree = findTree(treeName)
        }
        return deserializeTree(deserializedTree)
    }

    /**
     * Retrieves the names of all the trees stored in the database.
     *
     * @return a list of tree names
     */
    fun getAllTrees(): List<String> {
        val notes = mutableListOf<String>()
        connectDB()
        transaction {
            Trees.selectAll().forEach {
                val name = it[Trees.name]
                notes.add(name)
            }
        }

        return notes
    }
}
