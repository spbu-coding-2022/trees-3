package bst

import bst.nodes.BinaryNode

abstract class BalancingTree<K: Comparable<K>, V, Self: BinaryNode<K, V, Self>>: AbstractBST<K, V, Self>() {
    protected open fun rotateLeft(node: Self): Self {
        val right = node.right
        node.right = right?.left
        right?.left = node
        return right!!
    }
    protected open fun rotateRight(node: Self): Self {
        val left = node.left
        node.left = left?.right
        left?.right = node
        return left!!
    }
}
