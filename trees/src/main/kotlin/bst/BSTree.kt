package bst
import bst.nodes.BSTNode

import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

class BSTree<K: Comparable<K>, V>(@Transient val key: K? = null, @Transient val value: V? = null, treeName_: String=""): AbstractBST<K, V, BSTNode<K, V>>() {
    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)
    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
            treeName = treeName_
        }
    }

    //save_node is method for check recursive addition elements to db
    fun save_node(node: BSTNode<K, V>?, parentNode:BSTNode<K, V>? = null){
        node?.let {
            save_node(node.left, node)
            save_node(node.right, node)
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
    val test_data = BSTree("121", "dgs", "tree_1")
    test_data.insert("110", "dafad")
    test_data.insert("118", "adfaf")
    test_data.insert("124", "fggsg")
    test_data.saveTreeToJson()
    readFromJson("tree_1.json")
//    test_data.save_node(test_data.rootNode)
//    val json = Gson().toJson(test_data)
//    println(json)
//    val ret_obj = Gson().fromJson<BSTree<Comparable<Any>, Any>>(json, BSTree::class.java)

}
