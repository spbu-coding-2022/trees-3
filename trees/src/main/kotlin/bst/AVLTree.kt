package bst

import bst.nodes.AVLNode
import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : BalancingTree<K, V, AVLNode<K, V>>() {
    override fun initNode(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)

    override fun insertNode(node: AVLNode<K, V>?, key: K, value: V): AVLNode<K, V> {
        if (node == null) return initNode(key, value)
        if (key < node.key) {
            node.left = insertNode(node.left, key, value)
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value)
        } else {
            node.value = value
        }
        updateHeight(node)
        return balance(node)
    }

    private fun getHeight(node: AVLNode<K, V>?): Int {
        return node?.height ?: -1
    }

    private fun updateHeight(node: AVLNode<K, V>) {
        node.height = max(getHeight(node.left), getHeight(node.right)) + 1
    }

    private fun getBalanceFactor(node: AVLNode<K, V>?): Int = when (node) {
        null -> 0
        else -> getHeight(node.right) - getHeight(node.left)
    }

    private fun balance(node: AVLNode<K, V>): AVLNode<K, V> {
        return when (getBalanceFactor(node)) {
            -2 -> {
                if (getBalanceFactor(node.left) == 1) {
                    node.left = node.left?.let { rotateLeft(it) }
                }
                return rotateRight(node)
            }

            2 -> {
                if (getBalanceFactor(node.right) == -1) {
                    node.right = node.right?.let { rotateRight(it) }
                }
                return rotateLeft(node)
            }

            else -> node
        }
    }

    override fun removeNode(node: AVLNode<K, V>?, key: K): AVLNode<K, V>? {
        if (node == null) return null
        if (key < node.key) {
            node.left = removeNode(node.left, key)
        } else if (key > node.key) {
            node.right = removeNode(node.right, key)
        } else {
            if (node.left == null) {
                return node.right
            } else if (node.right == null) {
                return node.left
            } else {
                val tmp: AVLNode<K, V> =
                    findMax(node.left) ?: throw IllegalStateException("Left subtree must contain elements")
                node.key = tmp.key
                node.value = tmp.value
                node.left = removeNode(node.left, tmp.key)
            }
        }

        updateHeight(node)
        return balance(node)
    }

    override fun rotateRight(node: AVLNode<K, V>): AVLNode<K, V> {
        val left = node.left ?: throw IllegalStateException("Node's left child cannot be null")
        node.left = left.right
        left.right = node
        updateHeight(node)
        updateHeight(left)
        return left
    }

    override fun rotateLeft(node: AVLNode<K, V>): AVLNode<K, V> {
        val right = node.right ?: throw IllegalStateException("Node's right child cannot be null")
        node.right = right.left
        right.left = node
        updateHeight(node)
        updateHeight(right)
        return right
    }
}
