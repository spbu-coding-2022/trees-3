package bst.db.controllers

import bst.AVLTree
import bst.nodes.AVLNode
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class JsonController : Controller<AVLNode<Int, String>, AVLTree<Int, String>> {
    val folderPath = "json"
    override fun saveTree(tree: AVLTree<Int, String>, treeName: String) {
        val gson = Gson()
        try {
            val writer = FileWriter("${this.folderPath}/$treeName.json")
            gson.toJson(tree, writer)
            writer.close()
        } catch (e: Exception) {
            println("Write error")
        }
    }

    override fun getTree(treeName: String): AVLTree<Int, String>? {
        val gson = Gson()
        return try {
            val reader = FileReader("${this.folderPath}/$treeName.json")
            val tree = gson.fromJson(reader, AVLTree<Int, String>()::class.java)
            reader.close()
            tree
        } catch (e: Exception) {
            println("Read error")
            null
        }
    }

    override fun removeTree(treeName: String) {
        File("${this.folderPath}/$treeName.json").delete()
    }

    fun getAllTrees(): List<String> {
        val jsonFiles = File(folderPath).listFiles { file -> file.extension == "json" }
        val jsonNames = mutableListOf<String>()
        jsonFiles?.forEach { file ->
            jsonNames.add(file.name.removeSuffix(".json"))
        }
        return jsonNames
    }
}
