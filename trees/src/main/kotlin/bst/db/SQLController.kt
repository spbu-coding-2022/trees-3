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

    private fun serializeTree(tree: BSTree<*, *>): SerializableTree{
        val serializedTree = SerializableTree(treeName = tree.treeName, rootNode = tree.rootNode?.let { serializeNode(it) })
        serializedTree.treeName = tree.treeName
        serializedTree.rootNode = serializeNode(tree.rootNode)
        return serializedTree
    }

    private fun createTables(){
        SchemaUtils.create(Trees)
        SchemaUtils.create(Nodes)
    }


    private fun SerializableNode.toNodeEntity(treeEntity: Tree): Node {
        return Node.new {
            key = this@toNodeEntity.key
            value = this@toNodeEntity.value
            x = this@toNodeEntity.x
            y = this@toNodeEntity.y
            left = this@toNodeEntity.leftNode?.toNodeEntity(treeEntity)
            right = this@toNodeEntity.rightNode?.toNodeEntity(treeEntity)
            tree = treeEntity
        }
    }

    fun saveTreeToDB(tree: BSTree<*, *>){
        connectDB()
        val serializedTree = serializeTree(tree)
        transaction {
            addLogger(StdOutSqlLogger)
            createTables()
            val daoTree = Tree.new {
                name = serializedTree.treeName
            }
            daoTree.rootNode = serializedTree.rootNode?.toNodeEntity(daoTree)
        }
    }
}

//fun main(){
//    val test_data = BSTree(121, "dgs")
//    test_data.insert(110, "dafad")
//    test_data.insert(118, "adfaf")
//    test_data.insert(124, "fggsg")
//    test_data.setName("afefadsf")
//    val controller = SQLController()
////    val serializedTree = controller.serializeTree(test_data)
//    controller.saveTreeToDB(test_data)
//}
