package bst

import bst.nodes.RBTNode

class RedBlackTree<K : Comparable<K>, V> : BalancingTree<K, V>() {
    private var root: RBTNode<K, V>? = null

    private fun isRed(node: RBTNode<K, V>?): Boolean {
        return node?.red == true
    }

    override fun find(key: K): Boolean {
        return super.search(root, key) != null
        // return this.search(this.rootNode, key)
        // this method gives element by key
    }

    override fun insert(key: K, value: V) {
        insertNode(key, value)
    }

    fun insertNode(key: K, value: V) {
        if (root == null) {
            /* Empty tree case */
            root = RBTNode(key, value, false)
            return
        } else {
            val head = RBTNode(key, value) // False tree root

            var grandparent: RBTNode<K, V>? = null // Grandparent
            var t: RBTNode<K, V> = head // Parent
            var parent: RBTNode<K, V>? = null // Iterator

            t.right = root
            var q: RBTNode<K, V>? = t.right // Parent
            var dir = false // false - left, true - right
            var last = false

            // Search down the tree
            while (true) {
                if (q == null) {
                    // Insert new node at the bottom
                    q = RBTNode(key, value)
                    if (dir) parent?.right = q else parent?.left = q
                } else if (isRed(q.left) && isRed(q.right)) {
                    // Color flip
                    q.red = true
                    q.left!!.red = false
                    q.right!!.red = false
                }

                // Fix red violation
                if (isRed(q) && isRed(parent)) {
                    val dir2 = if (t.child(true) == grandparent) true else false// === or == hmmm
                    if (dir2) {
                        if (q == parent!!.child(last)) {
                            t.right = rotate(grandparent!!, !last)
                        } else {
                            t.right = doubleRotate(grandparent!!, !last)

                        }
                    }
                    else {
                        if (q == parent!!.child(last)) {
                            t.left = rotate(grandparent!!, !last)
                        } else {
                            t.left = doubleRotate(grandparent!!, !last)

                        }
                    }
                }

                // Stop if found
                if (q.key == key) {
                    q!!.value = value
                    break
                }

                last = dir
                dir = if (q.key < key) true else false

                // Update helpers
                if (grandparent != null) {
                    t = grandparent
                }

                grandparent = parent
                parent = q
                q = q.child(dir)
            }

            // Update root
            root = head.right
        }
    }


    private fun rotate(node: RBTNode<K,V>, dir: Boolean): RBTNode<K, V> {
        val save: RBTNode<K, V>
        if (dir) {
            save = rotateRight(node)
        }
        else {
            save = rotateLeft(node)
        }
        node.red = true
        save.red = false
        return save
    }

    private fun doubleRotate(node: RBTNode<K,V>, dir: Boolean): RBTNode<K, V> {
        if (dir) {
            node.left = rotate(node.left!!, false)
        }
        else {
            node.right = rotate(node.right!!, true)
        }
        return rotate(node, dir)
    }


    override fun remove(key: K) {
        TODO()
    }

    private fun removeNode(node: RBTNode<K, V>): RBTNode<K, V> {
        TODO()
    }
}
