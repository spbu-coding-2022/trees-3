import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import db.Trees
import db.Node
import db.Nodes
import db.Tree
import bst.BSTree
import bst.nodes.BSTNode

private const val dbPath = "exposed_database.db"
//not finaly сompleted
fun treeSave(tree: BSTree<*, *>){
//    transaction {
        val rootNode_ = Node.new {
            key=tree.rootNode?.key.toString()
            value=tree.rootNode?.value.toString()
        }
//        val TreeObject = Tree.new {
//            name = tree.treeName
//        }
//        TreeObject.rootNode = rootNode_
//        saveNodes(tree.rootNode, tree.rootNode, TreeObject)
//    }
}
//not finaly сompleted
fun saveNodes(node: BSTNode<*, *>?, parentNode:BSTNode<*, *>? = null, tree_: Tree){
    node?.let {
        saveNodes(node.left, node, tree_)
        saveNodes(node.right, node, tree_)
//        transaction {
//            if (parentNode == null){

//            }

        val leftChildNode = Node.new {
            key = parentNode?.left?.key.toString()
            value = parentNode?.left?.value.toString()
            tree = tree_
        }
        val rightChildNode = Node.new {
            key = parentNode?.right?.key.toString()
            value = parentNode?.right?.value.toString()
            tree = tree_

        }
        val parentNode_ = Node.new {
            key = parentNode?.key.toString()
            value = parentNode?.value.toString()
            left = leftChildNode
            right = rightChildNode
            }
//        println("parent node: ${parentNode?.key}: ${parentNode?.value} saving node: ${node.key}: ${node.value}")
    }
}


fun main() {
    Database.connect("jdbc:sqlite:$dbPath", driver = "org.sqlite.JDBC")
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Trees)
        SchemaUtils.create(Nodes)
        val test_data = BSTree("121", "dgs", "tree_1")
        test_data.insert("110", "dafad")
        test_data.insert("118", "adfaf")
        test_data.insert("124", "fggsg")
        treeSave(test_data)

        val treeObj = Tree.new {
            name = "Tree_1"
        }
        val rootNode = Node.new {
                key = "test"
                value = "234"
                tree = treeObj
            }
        treeObj.rootNode = rootNode
//        treeObj.delete()
    }
}