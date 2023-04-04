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

    private fun insertNode(key: K, value: V) {
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
                    val dir2 = t.child(true) == grandparent// === or == hmmm
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
                    q.value = value
                    break
                }

                last = dir
                dir = q.key < key

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
    override fun remove(key: K) {
        removeNode(key)
    }
    private fun removeNode(key: K): Int {
        if (root != null) {
            val head = RBTNode(key, "" as V) // False tree root
            var q: RBTNode<K, V>? = head
            var p: RBTNode<K, V>? = null
            var g: RBTNode<K, V>?  // Helpers
            var f: RBTNode<K, V>? = null /* Found item */
            var dir = true


            q?.right = root

            /*
              Search and push a red node down
              to fix red violations as we go
            */
            while (q!!.child(dir) != null) {
                val last = dir

                /* Move the helpers down */
                g = p
                p = q
                q = q.child(dir)
                dir = q!!.key < key

                /*
                  Save the node with matching data and keep
                  going; we'll do removal tasks at the end
                */
                if (q.key == key)
                    f = q

                /* Push the red node down with rotations and color flips */
                if (!isRed(q) && !isRed(q.child(dir))) {
                    if (isRed(q.child(!dir))) {
                        if (last) {
                            p.right = rotate(q, dir)
                        } else {
                            p.left = rotate(q, dir)
                        }
                        p = p.child(last)
                    }
                    else if (!isRed(q.child(!dir))) {
                        val s = p.child(!last)

                        if (s != null) {
                            if (!isRed(s.child(!last)) && !isRed(s.child(last))) {
                                /* Color flip */
                                p.red = false
                                s.red = true
                                q.red = true
                            } else {
                                val dir2 = (g!!.right == p)

                                if (isRed(s.child(last)))
                                    if (dir2) {
                                        g.right = doubleRotate(p, last)
                                    } else {
                                        g.left = doubleRotate(p, last)
                                    }
                                else if (isRed(s.child(!last)))
                                    if (dir2) {
                                        g.right = rotate(p, last)
                                    } else {
                                        g.left = rotate(p, last)
                                    }
                                /* Ensure correct coloring */
                                q.red = true
                                g.child(dir2)!!.red = true
                                g.child(dir2)!!.left!!.red = false
                                g.child(dir2)!!.right!!.red = false
                            }
                        }
                    }
                }
            }

            /* Replace and remove the saved node */
            if (f != null) {
                if (p!!.right == q) {
                    p.right = q.child(q.left == null)
                }
                else {
                    p.left = q.child(q.left == null)
                }
            }

            /* Update the root (it may be different) */
            root = head.child(true)

            /* Make the root black for simplified logic */
            if (root != null)
                root!!.red = false

        }

        return 1
    }

    private fun rotate(node: RBTNode<K,V>, dir: Boolean): RBTNode<K, V> {
        val save: RBTNode<K, V> = if (dir) {
            rotateRight(node)
        } else {
            rotateLeft(node)
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
}
