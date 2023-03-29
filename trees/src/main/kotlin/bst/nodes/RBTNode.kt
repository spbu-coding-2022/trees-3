package bst.nodes

// хз насчет data class, мб че-то заходим в самой ноде делать
class RBTNode<K : Comparable<K>, V> (
    override val key: K,
    override var value: V,
    var red: Boolean = true
) : TreeNode<K, V, RBTNode<K, V>> {
    override var left: RBTNode<K, V>? = null
    override var right: RBTNode<K, V>? = null
}
