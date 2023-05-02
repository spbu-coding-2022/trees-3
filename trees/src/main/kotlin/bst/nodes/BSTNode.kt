package bst.nodes

/**
 * Represents a node in a Binary Search Tree.
 *
 * @param K the type of the key.
 * @param V the type of the value.
 * @param key the key associated with this node.
 * @param value the value associated with this node.
 * @param BinaryNode the parent class representing a binary node.
 */

class BSTNode<K : Comparable<K>, V>(
    key: K,
    value: V
) : BinaryNode<K, V, BSTNode<K, V>>(key, value)
