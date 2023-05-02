package bst.nodes

/**
 * Represents a binary node in a binary tree.
 *
 * @param K the type of the key.
 * @param V the type of the value.
 * @param Self the type of the node itself.
 * @param key the key associated with this node.
 * @param value the value associated with this node.
 */

abstract class BinaryNode<K : Comparable<K>, V, Self : BinaryNode<K, V, Self>>(
    var key: K,
    var value: V
) {
    /**
     * Represents the left child node of this node.
     */
    var left: Self? = null

    /**
     * Represents the right child node of this node.
     */
    var right: Self? = null
}
