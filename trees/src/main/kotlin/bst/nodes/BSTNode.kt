package bst.nodes

class BSTNode<K : Comparable<K>, V> (
    override val key: K,
    override var value: V
) : TreeNode<K, V, BSTNode<K, V>> {
    override var left: BSTNode<K, V>? = null
    override var right: BSTNode<K, V>? = null
}
