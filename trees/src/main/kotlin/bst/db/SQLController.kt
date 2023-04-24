package bst.db
import bst.BSTree
import bst.nodes.BSTNode
import db.Node
import org.jetbrains.exposed.sql.*
import db.Trees
import db.Nodes
import db.Tree
import org.jetbrains.exposed.sql.transactions.transaction

private const val dbPath = "exposed_database.db"

class SQLController {
    private fun connectDB() {
        Database.connect("jdbc:sqlite:$dbPath", driver = "org.sqlite.JDBC")
    }

    private fun serializeNode(node: BSTNode<*, *>?): SerializableNode?{
        return if (node == null){
            null
        } else{
            val serializableNode = SerializableNode(key = node.key.toString(), value = node.value.toString(), leftNode = null, rightNode = null)
            serializableNode.rightNode = serializeNode(node.right)
            serializableNode.leftNode = serializeNode(node.left)
            serializableNode
        }
    }

    private fun serializeTree(tree: BSTree<*, *>): SerializableTree? {
        return tree.rootNode?.let { serializeNode(it) }
            ?.let { SerializableTree(treeName = tree.treeName, rootNode = it) }
    }

    private fun createTables(){
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

    fun saveTreeToDB(tree: BSTree<*, *>){
        connectDB()
        val serializedTree = serializeTree(tree)
        transaction {
            addLogger(StdOutSqlLogger)
            createTables()
            val daoTree = Tree.new {
                if(serializedTree!=null)
                name = serializedTree.treeName
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
            this@getSerializedNode.left?.getSerializedNode(treeDao),
            this@getSerializedNode.right?.getSerializedNode(treeDao),
        )

    }

    private fun findTree(treeName: String): SerializableTree?{
        connectDB()
        val treeDAO = Tree.find{Trees.name eq treeName}.firstOrNull() ?: return null
        return treeDAO.rootNode?.getSerializedNode(treeDAO)?.let {
            SerializableTree(
                treeName,
                it
            )
        }
    }

    private fun isNumeric(s: String): Boolean {
        return try {
            s.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    private fun deserializeNodeStringKey(node: SerializableNode?): BSTNode<String, String>? {
        return if (node == null){
            null
        } else{

            val deserializableNode = BSTNode(key = node.key, value = node.value)
            deserializableNode.right = deserializeNodeStringKey(node.rightNode)
            deserializableNode.left = deserializeNodeStringKey(node.leftNode)
            deserializableNode
        }
    }

    private fun deserializeNodeDoubleKey(node: SerializableNode?): BSTNode<Double, String>? {
        return if (node == null){
            null
        } else{
            val deserializableNode = BSTNode(key = node.key.toDouble(), value = node.value)
            deserializableNode.right = deserializeNodeDoubleKey(node.rightNode)
            deserializableNode.left = deserializeNodeDoubleKey(node.leftNode)
            deserializableNode
        }
    }

    private fun deserializeTree(tree: SerializableTree?): BSTree<*, *>?{
        if (tree != null) {
            return if (isNumeric(tree.rootNode.key)){
                val rootNode = deserializeNodeDoubleKey(tree.rootNode)
                val deserializedTree = BSTree(rootNode?.key, rootNode?.value)
                deserializedTree.rootNode = rootNode
                deserializedTree.setName(tree.treeName)
                deserializedTree
            } else{
                val rootNode = deserializeNodeStringKey(tree.rootNode)
                val deserializedTree = BSTree(rootNode?.key, rootNode?.value)
                deserializedTree.rootNode = rootNode
                deserializedTree.setName(tree.treeName)
                deserializedTree
            }
        }
    return null
    }

    fun getTree(treeName: String): BSTree<*, *>? {
        var undeserializedTree:SerializableTree? = null
        transaction {
            undeserializedTree = findTree(treeName)

        }
        return deserializeTree(undeserializedTree)
    }
}
