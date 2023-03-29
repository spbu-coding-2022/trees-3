package bst.nodes

// а надо ли нам тогда value? хер знает
interface TreeNode<K : Comparable<K>, V, SpecNode : TreeNode<K, V, SpecNode>> {
    val key: K
    var value: V
    var left: SpecNode?
    var right: SpecNode?
}
