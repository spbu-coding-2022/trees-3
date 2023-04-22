package bst

import bst.nodes.RBTNode

class RedBlackTree<K : Comparable<K>, V> : BalancingTree<K, V, RBTNode<K, V>>() {
    private fun isRed(node: RBTNode<K, V>?): Boolean {
        return node != null && node.color == RBTNode.Color.RED
    }

    override fun insert(key: K, value: V) {
        insertNode(key, value)
    }

    private fun insertNode(key: K, value: V) {
        if (rootNode == null) {
            /* Empty tree case */
            rootNode = initNode(key, value)
            rootNode?.color = RBTNode.Color.BLACK
            return
        } else {
            val head = initNode(key, value) // False tree root

            var grandparent: RBTNode<K, V>? = null
            var t: RBTNode<K, V> = head // Iterator
            var parent: RBTNode<K, V>? = null

            t.right = rootNode
            var q: RBTNode<K, V>? = t.right
            var dir = false // false - left, true - right
            var last = false

            // Search down the tree
            while (true) {
                if (q == null) {
                    // Insert new node at the bottom
                    q = initNode(key, value)
                    if (dir) parent?.right = q else parent?.left = q
                } else if (isRed(q.left) && isRed(q.right)) {
                    // Color flip
                    q.color = RBTNode.Color.RED
                    q.left?.color = RBTNode.Color.BLACK
                    q.right?.color = RBTNode.Color.BLACK
                }
                // Fix red violation
                if (isRed(q) && isRed(parent) && grandparent != null) {
                    val dir2 = t.child(true) == grandparent
                    if (dir2) {
                        if (q == parent?.child(last)) {
                            t.right = rotate(grandparent, !last)
                        } else {
                            t.right = doubleRotate(grandparent, !last)
                        }
                    } else {
                        if (q == parent?.child(last)) {
                            t.left = rotate(grandparent, !last)
                        } else {
                            t.left = doubleRotate(grandparent, !last)
                        }
                    }
                }

                // Stop if found
                if (q.key == key) {
                    q.value = value
                    break
                }

                last = dir
                dir = q.key < key

                // Update helpers
                t = grandparent ?: t
                grandparent = parent
                parent = q
                q = q.child(dir)
            }

            // Update root
            rootNode = head.right
        }
        rootNode?.color = RBTNode.Color.BLACK
    }
    override fun remove(key: K) {
        removeNode(key)
    }
    private fun removeNode(key: K): Int {
        if (rootNode != null) {
            val head = initNode(key, "" as V) // False tree root
            var q: RBTNode<K, V> = head
            var parent: RBTNode<K, V>? = null
            var grandparent: RBTNode<K, V>?
            var f: RBTNode<K, V>? = null /* Found item's parent */
            var dir = true

            q.right = rootNode

            /*
              Search and push a red node down
              to fix red violations as we go
            */
            while (q.child(dir) != null) {
                val last = dir

                /* Move the helpers down */
                grandparent = parent
                parent = q
                q = parent.child(dir) ?: throw IllegalStateException("Parent node cannot be null")
                dir = q.key < key

                /*
                  Save parent of the node with matching data and keep
                  going; we'll do removal tasks at the end
                */
                if (q.key == key) {
                    f = q
                }
                /* Push the red node down with rotations and color flips */
                if (!isRed(q) && !isRed(q.child(dir))) {
                    if (isRed(q.child(!dir))) {
                        if (last) {
                            parent.right = rotate(q, dir)
                        } else {
                            parent.left = rotate(q, dir)
                        }
                        parent = parent.child(last)
                    } else if (!isRed(q.child(!dir))) {
                        val s = parent.child(!last)

                        if (s != null) {
                            if (!isRed(s.child(!last)) && !isRed(s.child(last))) {
                                /* Color flip */
                                parent.color = RBTNode.Color.BLACK
                                s.color = RBTNode.Color.RED
                                q.color = RBTNode.Color.RED
                            } else {
                                val dir2 = (grandparent?.right ?: throw IllegalStateException("Grandparent node cannot be null")) == parent

                                if (isRed(s.child(last))) {
                                    if (dir2) {
                                        grandparent.right = doubleRotate(parent, last)
                                    } else {
                                        grandparent.left = doubleRotate(parent, last)
                                    }
                                } else if (isRed(s.child(!last))) {
                                    if (dir2) {
                                        grandparent.right = rotate(parent, last)
                                    } else {
                                        grandparent.left = rotate(parent, last)
                                    }
                                }
                                /* Ensure correct coloring */
                                q.color = RBTNode.Color.RED
                                grandparent.child(dir2)?.color = RBTNode.Color.RED
                                grandparent.child(dir2)?.left?.color = RBTNode.Color.BLACK
                                grandparent.child(dir2)?.right?.color = RBTNode.Color.BLACK
                            }
                        }
                    }
                }
            }

            /* Replace and remove the saved node */
            if (f != null) {
                f.key = q.key
                f.value = q.value
                if (parent?.right == q) {
                    parent.right = q.child(q.left == null)
                } else {
                    parent?.left = q.child(q.left == null)
                }
            }

            /* Update the root (it may be different) */
            rootNode = head.child(true)

            /* Make the root black for simplified logic */
            rootNode?.color = RBTNode.Color.BLACK
        }
        return 1
    }

    private fun rotate(node: RBTNode<K, V>, dir: Boolean): RBTNode<K, V> {
        val save: RBTNode<K, V> = if (dir) {
            rotateRight(node)
        } else {
            rotateLeft(node)
        }
        node.color = RBTNode.Color.RED
        save.color = RBTNode.Color.BLACK
        return save
    }

    private fun doubleRotate(node: RBTNode<K, V>, dir: Boolean): RBTNode<K, V> {
        if (dir) {
            node.left = node.left?.let { rotate(it, false) }
        } else {
            node.right = node.right?.let { rotate(it, true) }
        }
        return rotate(node, dir)
    }

    override fun initNode(key: K, value: V): RBTNode<K, V> = RBTNode(key, value)

    override fun rotateLeft(node: RBTNode<K, V>): RBTNode<K, V> {
        val right = node.right ?: throw IllegalStateException("Node's right child cannot be null")
        node.right = right.left
        right.left = node
        return right
    }

    override fun rotateRight(node: RBTNode<K, V>): RBTNode<K, V> {
        val left = node.left ?: throw IllegalStateException("Node's left child cannot be null")
        node.left = left.right
        left.right = node
        return left
    }
}
