package bst.nodes

class AVLNode<K: Comparable<K>, V>(
    key: K,
    value: V
) : BinaryNode<K, V, AVLNode<K, V>>(key, value) {
    var height: Int = 0
}
