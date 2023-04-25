package bst.db.controllers

import bst.AVLTree
import com.google.gson.Gson
import java.io.FileReader
import java.io.FileWriter

class JsonController {
    fun saveTreeToJson(tree: AVLTree<Int, String>) {
        val gson = Gson()
        try {
            val writer = FileWriter("${tree.treeName}.json")
            gson.toJson(tree, writer)
            writer.close()
        } catch (e: Exception) {
            println("Write error")
        }
    }

    fun readFromJson(treeName: String): AVLTree<Int, String>? {
        val gson = Gson()
        return try {
            val reader = FileReader("$treeName.json")
            val tree = gson.fromJson(reader, AVLTree<Int, String>()::class.java)
            reader.close()
            tree
        } catch (e: Exception) {
            println("read error")
            null
        }
    }
}
