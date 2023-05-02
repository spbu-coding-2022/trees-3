package bst.nodes

/**
 * Represents a node in an AVL Tree.
 *
 * @param K the type of the key.
 * @param V the type of the value.
 * @param key the key associated with this node.
 * @param value the value associated with this node.
 * @param BinaryNode the parent class representing a binary node.
 */

class AVLNode<K : Comparable<K>, V>(
    key: K,
    value: V
) : BinaryNode<K, V, AVLNode<K, V>>(key, value) {
    /**
     * Represents the height of the node.
     */
    var height: Int = 0
}
