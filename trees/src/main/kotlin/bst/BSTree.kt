package bst
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
//    test_data.saveTree()
//    readFromJson("tree_1.json")
//    test_data.save_node(test_data.rootNode)
//    val json = Gson().toJson(test_data)
//    println(json)
//    val ret_obj = Gson().fromJson<BSTree<Comparable<Any>, Any>>(json, BSTree::class.java)

}
