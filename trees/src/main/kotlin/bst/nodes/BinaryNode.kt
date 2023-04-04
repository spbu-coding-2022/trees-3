package bst.nodes

abstract class BinaryNode<K: Comparable<K>, V, Self: BinaryNode<K, V, Self>>(
     var key: K,
     var value: V
) {
    var left: Self? = null
    var right: Self? = null
}
