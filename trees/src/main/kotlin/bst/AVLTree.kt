// GPL-3.0-or-later
// <Here is a data structure that implements the binary search tree.>
// This file is part of Trees-3.
//
// Trees-3 is free software: you can redistribute and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the license or (at your option) any later version.
//
// Trees-3 is distributed in the hope that it will be useful, but WITHOUT ANY GUARANTEES; even without an implicit guarantee of merchantability or FITNESS FOR A PARTICULAR PURPOSE. For more information, see the GNU General Public License.
//
// You should have obtained a copy of the GNU General Public License with this program. If it is not, see <https://www.gnu.org/licenses/>.
//    Copyright (C) <2023>  <Nemakin Nikita Antonovich>

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
                val tmp: AVLNode<K, V> = findMax(node.left)!!
                node.key = tmp.key
                node.value = tmp.value
                node.left = removeNode(node.left, tmp.key)
            }
        }

        updateHeight(node)
        return balance(node)
    }

    override fun rotateRight(node: AVLNode<K, V>): AVLNode<K, V> {
        val tmp = super.rotateRight(node)
        updateHeight(node)
        updateHeight(tmp)
        return tmp
    }
    override fun rotateLeft(node: AVLNode<K, V>): AVLNode<K, V> {
        val tmp = super.rotateLeft(node)
        updateHeight(node)
        updateHeight(tmp)
        return tmp
    }
}
