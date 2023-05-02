package bst

import bst.nodes.BinaryNode

/**
 * An abstract class representing a balancing tree data structure that extends the AbstractBST class.
 *
 * @param K the type of keys maintained by this balancing tree.
 * @param V the type of mapped values.
 * @param Self the type of the binary nodes in this balancing tree.
 */

abstract class BalancingTree<K : Comparable<K>, V, Self : BinaryNode<K, V, Self>> : AbstractBST<K, V, Self>() {

    /**
     * Rotates the given node to the left to rebalance the tree.
     *
     * @param node the node to rotate.
     * @return the rotated node.
     */

    protected abstract fun rotateLeft(node: Self): Self

    /**
     * Rotates the given node to the right to rebalance the tree.
     *
     * @param node the node to rotate.
     * @return the rotated node.
     */

    protected abstract fun rotateRight(node: Self): Self
}
