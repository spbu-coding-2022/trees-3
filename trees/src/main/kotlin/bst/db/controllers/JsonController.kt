package bst.db.controllers

import bst.AVLTree
import bst.nodes.AVLNode
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonController: Controller<AVLNode<Int, String>, AVLTree<Int, String>> {
    override fun saveTree(tree: AVLTree<Int, String>, treeName: String) {
        val gson = Gson()
        try {
            val writer = FileWriter("$treeName.json")
            gson.toJson(tree, writer)
            writer.close()
        } catch (e: Exception) {
            println("Write error")
        }
    }

    override fun getTree(treeName: String): AVLTree<Int, String>? {
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

    override fun removeTree(treeName: String) {
        File("$treeName.json").delete()
    }
}
