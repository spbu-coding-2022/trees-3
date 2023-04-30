package bst

import bst.nodes.BSTNode
import bst.nodes.BinaryNode
import org.neo4j.ogm.annotation.GeneratedValue
import org.neo4j.ogm.annotation.Id
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.annotation.NodeEntity

@NodeEntity

abstract class AbstractBST<K: Comparable<K>, V, Self: BinaryNode<K, V, Self>> : Tree<K, V> {
    @Id
    @GeneratedValue
    val id: Long? = null
    var treeName: String = ""

    internal var rootNode: Self? = null

    fun setName(treeName: String){
        this.treeName = treeName
    }

    fun getRoot(): Self? = this.rootNode

    fun clear() {
        this.rootNode = null
    }

    protected abstract fun initNode(key: K, value: V): Self

    override fun insert(key: K, value: V) {
        rootNode = insertNode(rootNode, key, value)
    }
    protected open fun insertNode(node: Self?, key: K, value: V): Self {
        if (node == null) return initNode(key, value)
        if (key < node.key) {
            node.left = insertNode(node.left, key, value)
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value)
        } else {
            node.value = value
        }
        return node
    }


    override fun remove(key: K) {
        rootNode = removeNode(rootNode, key)
    }
    protected open fun removeNode(node: Self?, key: K): Self? {
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
                val tmp: Self = findMax(node.left)!!
                node.key = tmp.key
                node.value = tmp.value
                node.left = removeNode(node.left, tmp.key)
            }
        }
        return node
    }

    override fun find(key: K): V? = findNode(rootNode, key)
    private fun findNode(node: Self?, key: K): V? {
        return if (node == null) {
            null
        } else if (key == node.key) {
            node.value
        } else {
            if (key < node.key) {
                findNode(node.left, key)
            } else {
                findNode(node.right, key)
            }
        }
    }

    protected fun findMax(node: Self?): Self? = when {
        node == null -> null
        node.right == null -> node
        else -> findMax(node.right)
    }
}

