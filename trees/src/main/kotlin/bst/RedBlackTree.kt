package bst

import bst.nodes.RBTNode

class RedBlackTree<K : Comparable<K>, V> : BalancingTree<K, V, RBTNode<K, V>>() {
    private fun isRed(node: RBTNode<K, V>?): Boolean {
        return node?.red == true
    }

    override fun insert(key: K, value: V) {
        insertNode(key, value)
    }

    private fun insertNode(key: K, value: V): RBTNode<K, V> {
        if (rootNode == null) {
            rootNode = RBTNode(key, value, true)
            return rootNode!!
        }

        var node = rootNode
        var parent: RBTNode<K, V>? = null

        while (node != null) {
            parent = node

            when {
                key < node.key -> node = node.left
                key > node.key -> node = node.right
                else -> {
                    node.value = value
                    return node
                }
            }
        }

        val newNode = RBTNode(key, value, false)
        if (key < parent!!.key) {
            parent.left = newNode
        } else {
            parent.right = newNode
        }

        var current = newNode
        while (isRed(current) && isRed(current.left)) {
            var sibling = current.right
            if (isRed(sibling)) {
                current.red = true
                sibling!!.red = false
                current = rotateLeft(current)

                current.red = current.left!!.red
                current.left!!.red = true

                sibling = current.right
            }
            if (!isRed(sibling?.left) && !isRed(sibling?.right)) {
                sibling!!.red = true
                current = parent
            } else {
                if (!isRed(sibling?.right)) {
                    sibling!!.left?.red = false
                    sibling.red = true
                    current = rotateRight(sibling)

                    current.red = current.right!!.red
                    current.right!!.red = true

                    sibling = current.right
                }
                sibling!!.red = current.red
                current.red = true
                sibling.right?.red = true
                current = rotateLeft(current)

                current.red = current.left!!.red
                current.left!!.red = true

                break
            }
        }

        rootNode!!.red = true
        return current
    }

    override fun remove(key: K) {
        TODO()
    }

    private fun removeNode(node: RBTNode<K, V>): RBTNode<K, V> {
        TODO()
    }

    override fun initNode(key: K, value: V): RBTNode<K, V> {
        TODO("Not yet implemented")
    }
}
