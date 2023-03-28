package bst

import bst.nodes.TreeNode

abstract class BalancingTree<K: Comparable<K>, V>: BinarySearchTree<K, V>() {
    protected fun <SpecNode : TreeNode<K, V, SpecNode>> rotateLeft(node: SpecNode): SpecNode {
        val right = node.right
        node.right = right?.left
        right?.left = node
        return right!!
    }
    protected fun <SpecNode : TreeNode<K, V, SpecNode>> rotateRight(node: SpecNode): SpecNode {
        val left = node.left
        node.left = left?.right
        left?.right = node
        return left!!
    }
}