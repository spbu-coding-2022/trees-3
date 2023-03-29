package bst

import bst.nodes.RBTNode

class RedBlackTree<K: Comparable<K>, V>: Tree<K, V> {

    override fun find(key: K): Boolean{
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }
    override fun insert(key: K, value: V) {
        TODO()
    }

    private fun insertNode(node: RBTNode<K, V>): RBTNode<K, V> {
        TODO()
    }

    override fun remove(key: K, value: V) {

    }
    private fun removeNode(node: RBTNode<K, V>): RBTNode<K, V> {
        TODO()
    }

    private fun moveRedLeft(node: RBTNode<K, V>): RBTNode<K, V> {
        TODO()
    }

    private fun moveRedRight(node: RBTNode<K, V>): RBTNode<K, V> {
        TODO()
    }

    private fun rotateRight(node: RBTNode<K, V>) {
        TODO()
    }

    private fun rotateLeft(node: RBTNode<K, V>) {
        TODO()
    }

    private fun balance(node: RBTNode<K, V>) {
        TODO()
    }
}