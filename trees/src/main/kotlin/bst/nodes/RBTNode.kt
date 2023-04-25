package bst.nodes

class RBTNode<K : Comparable<K>, V> (
    key: K,
    value: V,
    var color: Color = Color.RED
) : BinaryNode<K, V, RBTNode<K, V>>(key, value) {
    internal fun child(f: Boolean) = if (f) right else left

    enum class Color {
        RED, BLACK
    }
}
