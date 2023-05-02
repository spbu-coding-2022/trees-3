package bst

import bst.nodes.BinaryNode

/**
 * An abstract class representing a binary search tree data structure.
 *
 * @param K the type of keys maintained by this binary search tree.
 * @param V the type of mapped values.
 * @param Self the type of the binary nodes in this binary search tree.
 */

abstract class AbstractBST<K : Comparable<K>, V, Self : BinaryNode<K, V, Self>> : Tree<K, V> {

    /**
     * The name of this binary search tree.
     */

    var treeName: String = ""

    /**
     * The root node of this binary search tree.
     */

    internal var rootNode: Self? = null

    /**
     * Sets the name of this binary search tree.
     * @param treeName the name of this binary search tree.
     */

    fun setName(treeName: String) {
        this.treeName = treeName
    }

    /**
     * Returns the root node of this binary search tree.
     *
     * @return the root node of this binary search tree.
     */

    fun getRoot(): Self? = this.rootNode

    /**
     * Clears this binary search tree by setting its root node to null.
     */

    fun clear() {
        this.rootNode = null
    }

    /**
     * Initializes a new binary node with the given key and value\.
     * @param key the key of the new node\.
     * @param value the value of the new node\.
     * @return a new binary node with the given key and value\.
     */

    protected abstract fun initNode(key: K, value: V): Self

    /**
     * Inserts a new node with the given key and value into this binary search tree\.
     * @param key the key of the new node\.
     * @param value the value of the new node\.
     */

    override fun insert(key: K, value: V) {
        rootNode = insertNode(rootNode, key, value)
    }

    /**
     * Inserts a new node with the given key and value into the binary search tree rooted at the given node\.
     *
     * @param node the root node of the binary search tree to insert the new node into\.
     * @param key the key of the new node\.
     * @param value the value of the new node\.
     * @return the root node of the binary search tree after the new node has been inserted\.
     */

    protected open fun insertNode(node: Self?, key: K, value: V): Self {
        if (node == null) return initNode(key, value)
        if (key < node.key) {
            node.left = insertNode(node.left, key, value)
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value)
        } else {
            node.value = value
        }
        return node
    }

    /**
     * Removes the node with the given key from the tree.
     * @param key the key of the node to remove
     */

    override fun remove(key: K) {
        rootNode = removeNode(rootNode, key)
    }

    /**
     * Recursively removes the node with the given key from the subtree rooted at the given node.
     * @param node the root of the subtree to remove the node from
     * @param key the key of the node to remove
     * @return the root of the updated subtree
     */

    protected open fun removeNode(node: Self?, key: K): Self? {
        if (node == null) return null
        if (key < node.key) {
            node.left = removeNode(node.left, key)
        } else if (key > node.key) {
            node.right = removeNode(node.right, key)
        } else {
            if (node.left == null) {
                return node.right
            } else if (node.right == null) {
                return node.left
            } else {
                val tmp: Self = findMax(node.left)!!
                node.key = tmp.key
                node.value = tmp.value
                node.left = removeNode(node.left, tmp.key)
            }
        }
        return node
    }

    /**
     * Finds the value associated with the given key in the tree.
     * @param key the key to search for in the tree
     * @return the value associated with the given key if it exists, otherwise null
     */

    override fun find(key: K): V? = findNode(rootNode, key)

    /**
     * Recursively searches for the value associated with the given key in the subtree rooted at the given node.
     * @param node the root of the subtree to search in
     * @param key the key to search for in the subtree
     * @return the value associated with the given key if it exists in the subtree, otherwise null
     */

    private fun findNode(node: Self?, key: K): V? {
        return if (node == null) {
            null
        } else if (key == node.key) {
            node.value
        } else {
            if (key < node.key) {
                findNode(node.left, key)
            } else {
                findNode(node.right, key)
            }
        }
    }

    /**
     * Recursively finds the node with the maximum key in the subtree rooted at the given node.
     * @param node the root of the subtree to search in
     * @return the node with the maximum key in the subtree, or null if the subtree is empty
     */

    protected fun findMax(node: Self?): Self? = when {
        node == null -> null
        node.right == null -> node
        else -> findMax(node.right)
    }
}
