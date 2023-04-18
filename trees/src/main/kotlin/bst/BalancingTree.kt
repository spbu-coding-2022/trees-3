package bst

import bst.nodes.BinaryNode

abstract class BalancingTree<K : Comparable<K>, V, Self : BinaryNode<K, V, Self>> : AbstractBST<K, V, Self>() {
    protected open fun rotateLeft(node: Self): Self {
        val right = node.right ?: throw IllegalStateException("Node's right child cannot be null")
        node.right = right.left
        right.left = node
        return right
    }
    protected open fun rotateRight(node: Self): Self {
        val left = node.left ?: throw NullPointerException("Node's left child cannot be null")
        node.left = left.right
        left.right = node
        return left
    }
}
