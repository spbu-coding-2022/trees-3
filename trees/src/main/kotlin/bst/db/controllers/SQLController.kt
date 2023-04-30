package bst.db.controllers

import bst.BSTree
import bst.db.serializeClasses.SerializableNode
import bst.db.serializeClasses.SerializableTree
import bst.nodes.BSTNode
import bst.db.models.sql.Node
import bst.db.models.sql.Trees
import bst.db.models.sql.Nodes
import bst.db.models.sql.Tree
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class SQLController : Controller<BSTNode<Int, String>, BSTree<Int, String>> {
    private fun connectDB() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/test",
            driver = "org.postgresql.Driver",
            user = "test",
            password = "test-test"
        )
    }

    override fun removeTree(treeName: String) {
        transaction {
            val treeEntity = Tree.find { (Trees.name eq treeName) }.firstOrNull()
            treeEntity?.delete()
        }
    }

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

    private fun serializeTree(tree: BSTree<Int, String>, treeName: String): SerializableTree? {
        return tree.rootNode?.let { serializeNode(it) }
            ?.let { SerializableTree(treeName = treeName, rootNode = it) }
    }

    private fun createTables() {
        SchemaUtils.create(Trees)
        SchemaUtils.create(Nodes)
    }

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

    private fun isNumeric(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

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

    override fun getTree(treeName: String): BSTree<Int, String>? {
        var deserializedTree: SerializableTree? = null
        transaction {
            deserializedTree = findTree(treeName)
        }
        return deserializeTree(deserializedTree)
    }
}
