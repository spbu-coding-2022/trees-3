package bst

import bst.nodes.BSTNode

class BinarySearchTree<K: Comparable<K>, V>: Tree<K, V> {
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
//        TODO("Not yet implemented")
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

    override fun remove(key: K, value: V) {
        //remove node by key
        TODO("Not yet implemented")
    }

    override fun find(key: K): V {
        //this method gives element by key
        TODO("Not yet implemented")
    }

    override fun clear() {
        //clear tree
        TODO("Not yet implemented")
    }

    fun symmetricalTreeTraversal(){

    }
}


fun main(){
    val test_tree = BinarySearchTree<Int, String>()
    test_tree.insert(234, "afaraf")
    test_tree.insert(235, "afaraf")
    test_tree.insert(232, "afaraf")
    println("afadf")
//    test_tree.rootNode = BSTNode(123, "erefe")
//    val test_1 = mutableListOf<Int>(12341, 324)
//    test_tree.rootNode!!.left = BSTNode(123, "world_0")
//    test_tree.rootNode!!.right = BSTNode(123, "world_1")
}
