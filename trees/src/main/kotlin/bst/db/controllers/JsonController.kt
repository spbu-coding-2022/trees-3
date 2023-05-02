package bst.db.controllers

import bst.AVLTree
import bst.nodes.AVLNode
import com.google.gson.Gson
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * The JsonController class implements the Controller interface for managing AVL trees stored in JSON files.
 */
class JsonController : Controller<AVLNode<Int, String>, AVLTree<Int, String>> {
    /**
     * The path to the folder where JSON files are stored.
     */
    val folderPath = "json"

    /**
     * Saves an AVL tree with the specified name to a JSON file.
     *
     * @param tree the AVL tree to save
     * @param treeName the name of the tree
     */
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

    /**
     * Retrieves an AVL tree with the specified name from a JSON file.
     *
     * @param treeName the name of the tree to retrieve
     * @return the retrieved AVL tree object, or null if the tree is not found or an error occurs
     */
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

    /**
     * Removes an AVL tree with the specified name by deleting the corresponding JSON file.
     *
     * @param treeName the name of the tree to remove
     */
    override fun removeTree(treeName: String) {
        File("${this.folderPath}/$treeName.json").delete()
    }

    /**
     * Retrieves a list of names of all available AVL trees stored as JSON files.
     *
     * @return a list of tree names
     */
    fun getAllTrees(): List<String> {
        val jsonFiles = File(folderPath).listFiles { file -> file.extension == "json" }
        val jsonNames = mutableListOf<String>()
        jsonFiles?.forEach { file ->
            jsonNames.add(file.name.removeSuffix(".json"))
        }
        return jsonNames
    }
}
