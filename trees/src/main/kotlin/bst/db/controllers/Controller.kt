package bst.db.controllers

import bst.AbstractBST
import bst.nodes.BinaryNode

interface Controller<N: BinaryNode<Int, String, N>, T: AbstractBST<Int, String, N>> {
    fun saveTree(tree: T, treeName: String)
    fun getTree(treeName: String): T?
    fun removeTree(treeName: String)
}