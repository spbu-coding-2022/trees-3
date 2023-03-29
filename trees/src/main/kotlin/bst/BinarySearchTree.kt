package bst

import bst.nodes.BSTNode

open class BinarySearchTree<K : Comparable<K>, V> : Tree<K, V> {
    var rootNode: BSTNode<K, V>? = null
    override fun insert(key: K, value: V) {
        val newNode = BSTNode(key, value)
        if (this.rootNode === null){
            this.rootNode = newNode
            return
        }
        else{
            insertRecursive(this.rootNode!!, newNode)
        }
        //add element
        //make element as a root, if root is null
        //if root is not null it adds element to left or right branch
        //if left or right is not null - it checks it, and adds it if right or left is null
    }

    private fun insertRecursive(currentNode: BSTNode<K, V>, newNode: BSTNode<K, V>){
        if(newNode.key < currentNode.key){
            if(currentNode.left?.key === null){
                currentNode.left = newNode
            }
            else{
                insertRecursive(currentNode.left!!, newNode)
            }
        }
        if(newNode.key > currentNode.key){
            if(currentNode.right?.key === null){
                currentNode.right = newNode
            }
            else{
                insertRecursive(currentNode.right!!, newNode)
            }
        }
    }

    override fun remove(key: K) {
        // remove node by key
        TODO("Not yet implemented")
    }
    override fun find(key: K): Boolean {
        return this.search(this.rootNode, key) != null
        // return this.search(this.rootNode, key)
        // this method gives element by key
    }
    
    private fun search(currentNode: BSTNode<K, V>?,  key: K): BSTNode<K, V>? {
        if (currentNode==null){
            return null
        }
        if (currentNode.key == key) {
            return currentNode
        }

        if (currentNode.key < key) {
           return search(currentNode.right, key)
        }
        if (currentNode.key > key) {
           return search(currentNode.left, key)
        }
        else{
            return null
        }
//        return currentNode
    }

    fun symmetricalTreeTraversal() {
        TODO()
    }

    override fun clear() {
        TODO("Not yet implemented")
    }
}

fun main() {
    val test_tree = BinarySearchTree<Int, String>()
    test_tree.insert(234, "afaraf")
    test_tree.insert(235, "afaraf")
    test_tree.insert(232, "afaraf")
    val res = test_tree.find(239)
    println("afadf")
//    test_tree.rootNode = BSTNode(123, "erefe")
//    val test_1 = mutableListOf<Int>(12341, 324)
//    test_tree.rootNode!!.left = BSTNode(123, "world_0")
//    test_tree.rootNode!!.right = BSTNode(123, "world_1")
}
