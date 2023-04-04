package bst

import bst.nodes.BSTNode

class BSTree<K: Comparable<K>, V> : AbstractBST<K, V, BSTNode<K, V>>() {
    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)
}
