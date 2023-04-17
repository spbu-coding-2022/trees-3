package bst.nodes

class BSTNode<K: Comparable<K>, V>(
    key: K,
    value: V
) : BinaryNode<K, V, BSTNode<K, V>>(key, value)