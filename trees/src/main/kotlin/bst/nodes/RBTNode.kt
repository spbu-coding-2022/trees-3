package bst.nodes

enum class Color{
    BLACK,
    RED
}

// хз насчет data class, мб че-то заходим в самой ноде делать
class RBTNode<K: Comparable<K>, V> (
    override val key: K,
    override var value: V,
): TreeNode<K, V, RBTNode<K, V>> {
    override var left: RBTNode<K, V>? = null
    override var right: RBTNode<K, V>? = null
    var color = Color.BLACK

    internal fun isBlack() {
        TODO()
    }
    internal fun isRed() {
        TODO()
    }
    internal fun flipColors() {
        TODO()
    }


}