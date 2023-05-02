package bst

import bst.nodes.BSTNode

/**
 * A binary search tree implementation using a linked structure.
 * @param K the type of keys stored in the tree, must implement Comparable
 * @param V the type of values stored in the tree
 * @param Self the type of the nodes in the tree, must extend BSTNode<K, V>
 */

class BSTree<K : Comparable<K>, V>(val key: K? = null, val value: V? = null) :
    AbstractBST<K, V, BSTNode<K, V>>() {

    /**
     * Initializes a new node with the given key and value.
     * @param key the key of the new node
     * @param value the value of the new node
     * @return the new node
     */

    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)

    /**
     * Constructs a new BSTree with the given key and value as the root node.
     * @param key the key of the root node
     * @param value the value of the root node
     */

    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
        }
    }
}
