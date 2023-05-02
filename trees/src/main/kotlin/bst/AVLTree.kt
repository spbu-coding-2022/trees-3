package bst

import bst.nodes.AVLNode
import kotlin.math.max

/**
 * This class represents an AVL tree data structure and extends the BalancingTree class.
 * @param <K> the type of keys in the AVL tree, must implement Comparable interface.
 * @param <V> the type of values in the AVL tree.
 */
class AVLTree<K : Comparable<K>, V>(@Transient val key: K? = null, @Transient val value: V? = null) :
    BalancingTree<K, V, AVLNode<K, V>>() {

    /**
     * Initializes a new AVL node with the given key and value.
     * @param key the key of the node.
     * @param value the value of the node.
     * @return a new AVL node with the given key and value.
     */
    override fun initNode(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)

    /**
     * Initializes an AVL tree with a root node if the key and value are not null.
     * @param key the key of the root node.
     * @param value the value of the root node.
     */
    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
        }
    }

    /**
     * Inserts a new node with the given key and value into the AVL tree.
     * @param node the current AVL node being examined, can be null.
     * @param key the key of the new node to be inserted.
     * @param value the value of the new node to be inserted.
     * @return the AVL node that is the root of the subtree after insertion and rebalancing.
     */
    override fun insertNode(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V> {
        if (node == null) return initNode(key, value)
        if (key < node.key) {
            node.left = insertNode(node.left, key, value)
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value)
        } else {
            node.value = value
        }
        updateHeight(node)
        return balance(node)
    }

    /**
     * Gets the height of the given AVL node.
     * @param node the AVL node whose height is to be returned, can be null.
     * @return the height of the AVL node if it is not null, -1 otherwise.
     */
    private fun getHeight(node: AVLNode<K, V>?): Int {
        return node?.height ?: -1
    }

    /**
     * Updates the height of the given AVL node based on the heights of its left and right subtrees.
     * @param node the AVL node whose height is to be updated.
     */
    private fun updateHeight(node: AVLNode<K, V>) {
        node.height = max(getHeight(node.left), getHeight(node.right)) + 1
    }

    /**
     * Gets the balance factor of the given AVL node.
     * @param node the AVL node whose balance factor is to be returned, can be null.
     * @return the balance factor of the AVL node if it is not null, 0 otherwise.
     */
    private fun getBalanceFactor(node: AVLNode<K, V>?): Int = when (node) {
        null -> 0
        else -> getHeight(node.right) - getHeight(node.left)
    }

    /**
     * Balances the given AVL node by performing rotations if necessary.
     * @param node the AVL node to be balanced.
     * @return the AVL node that is the root of the subtree after balancing.
     */
    private fun balance(node: AVLNode<K, V>): AVLNode<K, V> {
        return when (getBalanceFactor(node)) {
            -2 -> {
                if (getBalanceFactor(node.left) == 1) {
                    node.left = node.left?.let { rotateLeft(it) }
                }
                return rotateRight(node)
            }

            2 -> {
                if (getBalanceFactor(node.right) == -1) {
                    node.right = node.right?.let { rotateRight(it) }
                }
                return rotateLeft(node)
            }

            else -> node
        }
    }

    /**
     * Removes a node with the given key from the AVL tree starting at the given node.
     *
     * @param node The root node of the AVL tree or a subtree.
     * @param key The key of the node to be removed.
     * @return The root node of the AVL tree or a subtree after the removal.
     */
    override fun removeNode(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
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
                // Find the maximum node in the left subtree
                val tmp: AVLNode<K, V> =
                    findMax(node.left) ?: throw IllegalStateException("Left subtree must contain elements")
                // Replace the node to be removed with the maximum node in the left subtree
                node.key = tmp.key
                node.value = tmp.value
                // Remove the maximum node in the left subtree
                node.left = removeNode(node.left, tmp.key)
            }
        }
        // Update the height of the node after the removal
        updateHeight(node)
        // Balance the node after the removal
        return balance(node)
    }

    /**
     * Rotates the given AVL node to the right.
     *
     * @param node The AVL node to be rotated.
     * @return The AVL node that has been rotated.
     * @throws IllegalStateException If the node's left child is null.
     */
    override fun rotateRight(node: AVLNode<K, V>): AVLNode<K, V> {
        val left = node.left ?: throw IllegalStateException("Node's left child cannot be null")
        node.left = left.right
        left.right = node
        // Update the height of the nodes after the rotation
        updateHeight(node)
        updateHeight(left)
        return left
    }

    /**
     * Rotates the given AVL node to the left.
     *
     * @param node The AVL node to be rotated.
     * @return The AVL node that has been rotated.
     * @throws IllegalStateException If the node's right child is null.
     */
    override fun rotateLeft(node: AVLNode<K, V>): AVLNode<K, V> {
        val right = node.right ?: throw IllegalStateException("Node's right child cannot be null")
        node.right = right.left
        right.left = node
        // Update the height of the nodes after the rotation
        updateHeight(node)
        updateHeight(right)
        return right
    }
}
