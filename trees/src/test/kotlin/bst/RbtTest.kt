package bst

import bst.nodes.RBTNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RbtTest {
    /*
    This function makes sure that a tree is indeed red-black by walking over
    every node and checking invariants. If it returns 0 that means
    the tree is invalid red-black tree, and any other number is the black height
    of the entire tree.
     */
    private fun <K : Comparable<K>, V>isRbt(root: RBTNode<K, V>?): Int {
        if (root == null) {
            return 1
        }
        val left = root.left
        val right = root.right
        if (isRed(root)) {
            if (isRed(left) && isRed(right)) {
                return 0
            }
        }
        val leftHeight = isRbt(left)
        val rightHeight = isRbt(right)

        // Invalid binary search tree
        if ((left != null && left.key >= root.key) || (right != null && right.key <= root.key)) {
            return 0
        }

        // Black height mismatch
        if (leftHeight != 0 && rightHeight != 0 && leftHeight != rightHeight) {
            return 0
        }

        // Counting black links
        return if (leftHeight != 0 && rightHeight != 0) {
            if (isRed(root)) leftHeight else leftHeight + 1
        } else {
            0
        }
    }

    private fun <K : Comparable<K>, V> isRed(node: RBTNode<K, V>?): Boolean {
        return node != null && node.color == RBTNode.Color.RED
    }

    private fun <K : Comparable<K>, V> countNodes(node: RBTNode<K, V>?): Int {
        if (node == null) return 0
        return countNodes(node.left) + countNodes(node.right) + 1
    }

    private lateinit var tree: RedBlackTree<Int, String>
    private val values = IntArray(1_000_000) { it + 1 }

    @BeforeEach
    fun initializeObjects() {
        tree = RedBlackTree()
    }

    @Test
    fun `validate tree if empty`() {
        assertNull(tree.rootNode)
        assertEquals(0, countNodes(tree.rootNode))
        assertNotEquals(0, isRbt(tree.rootNode))
    }

    @Test
    fun `verify invariants after each action`() {
        values.take(1000).forEach {
            tree.insert(it, it.toString())
            assertNotEquals(0, isRbt(tree.rootNode))
        }
        values.take(500).forEach {
            tree.remove(it)
            assertNotEquals(0, isRbt(tree.rootNode))
        }
        assertEquals(500, countNodes(tree.rootNode))
    }

    @Nested
    inner class InsertionTests {
        @Test
        fun `insert single key-value pair`() {
            tree.insert(1, "A")
            assertEquals(1, tree.rootNode?.key)
            assertEquals(1, countNodes(tree.rootNode))
            assertNotEquals(0, isRbt(tree.rootNode))
        }

        @Test
        fun `insert a few key-value pairs`() {
            tree.insert(19, "S")
            tree.insert(5, "E")
            tree.insert(1, "A")
            tree.insert(18, "R")
            assertEquals("E", tree.rootNode?.let { tree.find(it.key) })
            assertEquals("A", tree.rootNode?.left?.let { tree.find(it.key) })
            assertEquals(null, tree.find(10))

            assertEquals(4, countNodes(tree.rootNode))
            assertNotEquals(0, isRbt(tree.rootNode))
        }

        @Test
        fun `insert same key twice`() {
            tree.insert(1, "A")
            tree.insert(1, "B")
            assertEquals(1, countNodes(tree.rootNode))
            assertNotEquals(0, isRbt(tree.rootNode))
            assertEquals("B", tree.find(1))
        }
    }

    @Nested
    inner class DeleteTests {
        @Test
        fun `delete one key-value pair`() {
            tree.insert(2, "B")
            tree.remove(2)
            assertEquals(0, countNodes(tree.rootNode))
            assertNotEquals(0, isRbt(tree.rootNode))
        }

        @Test
        fun `delete non-existent key-value pair`() {
            tree.insert(2, "B")
            tree.remove(3)
            assertEquals(1, countNodes(tree.rootNode))
            assertNotEquals(0, isRbt(tree.rootNode))
        }

        @Test
        fun `delete a few key-value pairs`() {
            tree.insert(19, "S")
            tree.insert(5, "E")
            tree.insert(1, "A")
            tree.insert(18, "R")
            tree.remove(1)
            tree.remove(19)
            assertEquals("R", tree.find(18))
            assertEquals(null, tree.find(1))
            tree.remove(18)
            assertEquals("E", tree.find(5))
            assertEquals(null, tree.find(18))
            assertNotEquals(0, isRbt(tree.rootNode))
        }

        @Test
        fun `delete many key-value pairs`() {
            values.shuffle()
            values.forEach { tree.insert(it, it.toString()) }
            values.take(500_000).forEach { tree.remove(it) }
            assertEquals(500_000, countNodes(tree.rootNode))
            assertNotEquals(0, isRbt(tree.rootNode))
        }
    }
}
