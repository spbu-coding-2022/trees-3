package bst.nodes

class RBTNode<K: Comparable<K>, V> (
    key: K,
    value: V,
    var red: Boolean = true
): BinaryNode<K, V, RBTNode<K, V>>(key, value) {
    internal fun child(f: Boolean) = if (f) right else left
}
