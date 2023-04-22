package bst

import bst.nodes.BinaryNode

abstract class BalancingTree<K : Comparable<K>, V, Self : BinaryNode<K, V, Self>> : AbstractBST<K, V, Self>() {
    protected abstract fun rotateLeft(node: Self): Self
    protected abstract fun rotateRight(node: Self): Self
}
