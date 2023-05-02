package bst.db.controllers

import bst.AbstractBST
import bst.nodes.BinaryNode

/**
 * The Controller interface defines the operations for managing binary search trees (BSTs) in the database.
 *
 * @param N the type of BinaryNode used in the BST
 * @param T the type of AbstractBST implementation
 */
interface Controller<N : BinaryNode<Int, String, N>, T : AbstractBST<Int, String, N>> {
    /**
     * Saves a BST with the specified name to the database.
     *
     * @param tree the BST to save
     * @param treeName the name of the BST
     */
    fun saveTree(tree: T, treeName: String)

    /**
     * Retrieves a BST with the specified name from the database.
     *
     * @param treeName the name of the BST to retrieve
     * @return the retrieved BST object, or null if the BST is not found
     */
    fun getTree(treeName: String): T?

    /**
     * Removes a BST with the specified name from the database.
     *
     * @param treeName the name of the BST to remove
     */
    fun removeTree(treeName: String)
}
