package bst

import bst.nodes.RBTNode

/**
 * RedBlackTree represents a red-black tree data structure and extends the BalancingTree class.
 *
 * @param <K> The type of the keys in the tree, which must be comparable.
 * @param <V> The type of the values in the tree.
 * @property key The key of the root node of the tree.
 * @property value The value of the root node of the tree.
 * @constructor Creates a new instance of the RedBlackTree class with the given key and value.
 */
class RedBlackTree<K : Comparable<K>, V>(@Transient val key: K? = null, @Transient val value: V? = null) : BalancingTree<K, V, RBTNode<K, V>>() {

    /**
     * Initializes a new Red-Black Tree node with the given key and value.
     *
     * @param key The key of the new node.
     * @param value The value of the new node.
     * @return The new node.
     */
    override fun initNode(key: K, value: V): RBTNode<K, V> = RBTNode(key, value)

    /**
     * Initializes the root node of the tree with the given key and value if they are not null.
     */
    init {
        if (key != null && value != null) {
            rootNode = initNode(key, value)
        }
    }

    /**
     * Checks if the given Red-Black Tree node is red.
     *
     * @param node The node to check.
     * @return true if the node is not null and has the color red, false otherwise.
     */
    private fun isRed(node: RBTNode<K, V>?): Boolean {
        return node != null && node.color == RBTNode.Color.RED
    }

    /**
     * Inserts a new node with the given key and value into the Red-Black Tree.
     *
     * @param key The key of the new node.
     * @param value The value of the new node.
     */
    override fun insert(key: K, value: V) {
        insertNode(key, value)
    }

    /**
     * Inserts a new node with the given key and value into the Red-Black Tree.
     *
     * @param key The key of the new node.
     * @param value The value of the new node.
     */
    private fun insertNode(key: K, value: V) {
        if (rootNode == null) {
            /* Empty tree case */
            rootNode = initNode(key, value)
            rootNode?.color = RBTNode.Color.BLACK
            return
        } else {
            val head = initNode(key, value) // False tree root

            var grandparent: RBTNode<K, V>? = null
            var helper: RBTNode<K, V> = head
            var parent: RBTNode<K, V>? = null

            helper.right = rootNode
            var iter: RBTNode<K, V>? = helper.right
            /*
              true is for right child
              false is for left child
             */
            var direction = false
            /* Last direction */
            var last = false

            // Search down the tree
            while (true) {
                if (iter == null) {
                    // Insert new node at the bottom
                    iter = initNode(key, value)
                    if (direction) parent?.right = iter else parent?.left = iter
                } else if (isRed(iter.left) && isRed(iter.right)) {
                    // Color flip
                    iter.color = RBTNode.Color.RED
                    iter.left?.color = RBTNode.Color.BLACK
                    iter.right?.color = RBTNode.Color.BLACK
                }
                // Fix red violation
                if (isRed(iter) && isRed(parent) && grandparent != null) {
                    val dir2 = helper.child(true) == grandparent
                    if (dir2) {
                        if (iter == parent?.child(last)) {
                            helper.right = rotate(grandparent, !last)
                        } else {
                            helper.right = doubleRotate(grandparent, !last)
                        }
                    } else {
                        if (iter == parent?.child(last)) {
                            helper.left = rotate(grandparent, !last)
                        } else {
                            helper.left = doubleRotate(grandparent, !last)
                        }
                    }
                }

                // Stop if found
                if (iter.key == key) {
                    iter.value = value
                    break
                }

                last = direction
                direction = iter.key < key

                // Update helpers
                helper = grandparent ?: helper
                grandparent = parent
                parent = iter
                iter = iter.child(direction)
            }

            // Update root
            rootNode = head.right
        }
        rootNode?.color = RBTNode.Color.BLACK
    }

    /**
     * Removes the node with the given key from the binary search tree.
     * This function calls the removeNode function with the given key as its parameter.
     * @param key the key of the node to be removed
     */
    override fun remove(key: K) {
        removeNode(key)
    }

    /**
     * Update the root of the Red-Black tree to the head child node and make the root black for simplified logic.
     * @return 1 after the root is updated and colored black
     * Removes the node with the given key from the binary search tree.
     * @param key the key of the node to be removed
     * @throws IllegalStateException if the root node of the tree is null
     */

    private fun removeNode(key: K): Int {
        // Code implementation for removing node from the binary search tree
        if (rootNode != null) {
            /*
              False tree root.
             */
            val head = initNode(
                key,
                rootNode?.value
                    ?: throw IllegalStateException("Root of the tree cannot be null")
            )
            var iter: RBTNode<K, V> = head
            var parent: RBTNode<K, V>? = null
            var grandparent: RBTNode<K, V>?
            var nodeToDelete: RBTNode<K, V>? = null
            /*
              true is for right child
              false is for left child
             */
            var direction = true

            iter.right = rootNode

            /*
              Search and push a red node down
              to fix red violations as we go
            */
            while (iter.child(direction) != null) {
                val last = direction

                /* Move the helpers down */
                grandparent = parent
                parent = iter
                iter = parent.child(direction) ?: throw IllegalStateException("Parent node cannot be null")
                direction = iter.key < key

                /*
                  Save the node with matching data and keep
                  going; we'll do removal tasks at the end
                */
                if (iter.key == key) {
                    nodeToDelete = iter
                }
                /* Push the red node down with rotations and color flips */
                if (!isRed(iter) && !isRed(iter.child(direction))) {
                    if (isRed(iter.child(!direction))) {
                        if (last) {
                            parent.right = rotate(iter, direction)
                        } else {
                            parent.left = rotate(iter, direction)
                        }
                        parent = parent.child(last)
                    } else if (!isRed(iter.child(!direction))) {
                        val sibling = parent.child(!last)

                        if (sibling != null) {
                            if (!isRed(sibling.child(!last)) && !isRed(sibling.child(last))) {
                                /* Color flip */
                                parent.color = RBTNode.Color.BLACK
                                sibling.color = RBTNode.Color.RED
                                iter.color = RBTNode.Color.RED
                            } else {
                                val direction2 = (
                                    grandparent?.right
                                        ?: throw IllegalStateException("Grandparent node cannot be null")
                                    ) == parent

                                if (isRed(sibling.child(last))) {
                                    if (direction2) {
                                        grandparent.right = doubleRotate(parent, last)
                                    } else {
                                        grandparent.left = doubleRotate(parent, last)
                                    }
                                } else if (isRed(sibling.child(!last))) {
                                    if (direction2) {
                                        grandparent.right = rotate(parent, last)
                                    } else {
                                        grandparent.left = rotate(parent, last)
                                    }
                                }
                                /* Ensure correct coloring */
                                iter.color = RBTNode.Color.RED
                                grandparent.child(direction2)?.color = RBTNode.Color.RED
                                grandparent.child(direction2)?.left?.color = RBTNode.Color.BLACK
                                grandparent.child(direction2)?.right?.color = RBTNode.Color.BLACK
                            }
                        }
                    }
                }
            }

            /* Replace and remove the saved node */
            if (nodeToDelete != null) {
                nodeToDelete.key = iter.key
                nodeToDelete.value = iter.value
                if (parent?.right == iter) {
                    parent.right = iter.child(iter.left == null)
                } else {
                    parent?.left = iter.child(iter.left == null)
                }
            }

            /* Update the root (it may be different) */
            rootNode = head.child(true)

            /* Make the root black for simplified logic */
            rootNode?.color = RBTNode.Color.BLACK
        }
        return 1
    }

    /**
     * Performs a single rotation on the given node in the specified direction.
     * @param node the node to be rotated
     * @param dir the direction of the rotation (true for right, false for left)
     * @return the child node after the rotation
     */

    private fun rotate(node: RBTNode<K, V>, dir: Boolean): RBTNode<K, V> {
        // Code implementation for performing a single rotation on the given node
        val childNode: RBTNode<K, V> = if (dir) {
            rotateRight(node)
        } else {
            rotateLeft(node)
        }
        node.color = RBTNode.Color.RED
        childNode.color = RBTNode.Color.BLACK
        return childNode
    }

    /**
     * Performs a double rotation on the given node in the specified direction.
     * @param node the node to be double rotated
     * @param dir the direction of the double rotation (true for right, false for left)
     * @return the child node after the double rotation
     */

    private fun doubleRotate(node: RBTNode<K, V>, dir: Boolean): RBTNode<K, V> {
        if (dir) {
            node.left = node.left?.let { rotate(it, false) }
        } else {
            node.right = node.right?.let { rotate(it, true) }
        }
        return rotate(node, dir)
    }

    /**
     * Rotates the given node to the left to rebalance the tree.
     * @param node the node to be rotated to the left
     * @return the new node after the rotation
     * @throws IllegalStateException if the node's right child is null
     */

    override fun rotateLeft(node: RBTNode<K, V>): RBTNode<K, V> {
        val right = node.right ?: throw IllegalStateException("Node's right child cannot be null")
        node.right = right.left
        right.left = node
        return right
    }

    /**
     * Rotates the given node to the right to rebalance the tree.
     * @param node the node to be rotated to the right
     * @return the new node after the rotation
     * @throws IllegalStateException if the node's left child is null
     */

    override fun rotateRight(node: RBTNode<K, V>): RBTNode<K, V> {
        val left = node.left ?: throw IllegalStateException("Node's left child cannot be null")
        node.left = left.right
        left.right = node
        return left
    }
}
