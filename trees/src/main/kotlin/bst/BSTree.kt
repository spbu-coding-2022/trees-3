package bst
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.session.query
import bst.nodes.BSTNode

import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

class BSTree<K: Comparable<K>, V>(@Transient val key: K? = null, @Transient val value: V? = null): AbstractBST<K, V, BSTNode<K, V>>() {
    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)
    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
        }
    }

    fun setName(treeName: String){
        this.treeName = treeName
    }

    //save_node is method for check recursive addition elements to db
    fun saveTree(node: BSTNode<K, V>? = this.rootNode, parentNode:BSTNode<K, V>? = this.rootNode){
        node?.let {
            saveTree(node.left, node)
//            println("parent node: ${parentNode?.key}: ${parentNode?.value} saving node: ${node.key}: ${node.value}")

//            println("${node.key}: ${node.value}")
            saveTree(node.right, node)
//            println("parent node: ${node.key}: ${node.value} saving left node: ${node.left?.key}: ${node.left?.value} saving right node: ${node.right?.key}: ${node.right?.value}")

//            println("${node.key}: ${node.value}")
//            println("parent node: ${null}: ${null} saving node: ${node.key}: ${node.value}")
//            if (parentNode?.key == node.key){
//                println("parent node: ${node.key}: ${node.value} saving left node: ${node.left?.key}: ${node.left?.value} saving right node: ${node.right?.key}: ${node.right?.value}")
//            }
//            else
            println("parent node: ${parentNode?.key}: ${parentNode?.value} saving node: ${node.key}: ${node.value}")
        }
    }

    fun saveTreeToJson(){
        val gson = Gson()
        val writer = FileWriter("${this.treeName}.json")
        val treeToSave = this
        gson.toJson(treeToSave, writer)
        writer.close()
    }

}
fun readFromJson(treeName: String): BSTree<*, *>{
    val gson = Gson()
    val reader = FileReader(treeName)
    val tree = gson.fromJson(reader, BSTree::class.java)
    reader.close()
    return tree
}


fun main(){
    val test_data = BSTree("121", "dgs")
    test_data.insert("110", "dafad")
    test_data.insert("118", "adfaf")
    test_data.insert("124", "fggsg")
    test_data.insert("127", "fggsg")
    test_data.insert("123", "fggsg")
    test_data.setName("etst")
//    test_data.saveTreeToJson()
    test_data.saveTree()
//    readFromJson("tree_1.json")
//    test_data.save_node(test_data.rootNode)
//    val json = Gson().toJson(test_data)
//    println(json)
//    val ret_obj = Gson().fromJson<BSTree<Comparable<Any>, Any>>(json, BSTree::class.java)

}
