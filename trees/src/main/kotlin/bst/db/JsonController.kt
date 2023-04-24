package bst.db

import bst.AVLTree
import bst.BSTree
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

class JsonController{
    fun saveTreeToJson(tree: AVLTree<*, *>){
        val gson = Gson()
        val writer = FileWriter("${tree.treeName}.json")
        val treeToSave = tree
        gson.toJson(treeToSave, writer)
        writer.close()
    }
    fun readFromJson(treeName: String): BSTree<*, *> {
        val gson = Gson()
        val reader = FileReader("$treeName.json")
        val tree = gson.fromJson(reader, BSTree::class.java)
        reader.close()
        return tree
    }
}
