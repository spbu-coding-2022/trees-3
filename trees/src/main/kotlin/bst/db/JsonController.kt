package bst.db

import bst.AVLTree
import bst.BSTree
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

class JsonController{
    fun saveTreeToJson(tree: AVLTree<*, *>){
        val gson = Gson()
//        val writer: FileWriter
        try {
            val writer = FileWriter("${tree.treeName}.json")
            gson.toJson(tree, writer)
            writer.close()
        }catch (e: Exception){
            println("Write error")
        }
    }
    fun readFromJson(treeName: String): BSTree<*, *>? {
        val gson = Gson()
        return try{
            val reader = FileReader("$treeName.json")
            val tree = gson.fromJson(reader, BSTree::class.java)
            reader.close()
            tree
        }catch (e: Exception){
            println("read error")
            null
        }
    }
}
