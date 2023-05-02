package bst

import bst.nodes.AVLNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.math.max

class AvlTest {
    private fun <K : Comparable<K>, V> isAvl(node: AVLNode<K, V>?): Boolean {
        if (node == null) return true
        return isAvl(node.left) &&
            isAvl(node.right) &&
            (subtreeHeight(node.left) - subtreeHeight(node.right) in -1..1)
    }

    private fun <K : Comparable<K>, V> subtreeHeight(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return max(subtreeHeight(node.left), subtreeHeight(node.right)) + 1
    }

    private fun <K : Comparable<K>, V> countNodes(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return countNodes(node.left) + countNodes(node.right) + 1
    }

    private lateinit var tree: AVLTree<Int, Int>
    private val values = IntArray(1000) { it + 1 }

    @BeforeEach
    fun initializeObjects() {
        tree = AVLTree()
    }

    @Test
    fun `validate tree if empty`() {
        assertNull(tree.rootNode)
        assertEquals(0, countNodes(tree.rootNode))
        assertTrue(isAvl(tree.rootNode))
    }

    @Nested
    inner class InsertionTests {
        @Test
        fun `insert a few key-value pairs`() {
            tree.insert(2, 2)
            tree.insert(3, 3)
            tree.insert(1, 1)

            assertEquals(2, tree.rootNode?.key)
            assertEquals(3, tree.rootNode?.right?.key)
            assertEquals(1, tree.rootNode?.left?.key)

            assertEquals(3, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `check left-left rotation case`() {
            tree.insert(3, 3)
            tree.insert(2, 2)
            tree.insert(1, 1)

            assertEquals(2, tree.rootNode?.key)
            assertEquals(1, tree.rootNode?.left?.key)
            assertEquals(3, tree.rootNode?.right?.key)

            assertEquals(3, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `check left-right rotation case`() {
            tree.insert(5, 5)
            tree.insert(3, 3)
            tree.insert(4, 4)

            assertEquals(4, tree.rootNode?.key)
            assertEquals(3, tree.rootNode?.left?.key)
            assertEquals(5, tree.rootNode?.right?.key)

            assertEquals(3, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `check right-right rotation case`() {
            tree.insert(3, 3)
            tree.insert(4, 4)
            tree.insert(5, 5)

            assertEquals(4, tree.rootNode?.key)
            assertEquals(3, tree.rootNode?.left?.key)
            assertEquals(5, tree.rootNode?.right?.key)

            assertEquals(3, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `check right-left rotation case`() {
            tree.insert(3, 3)
            tree.insert(5, 5)
            tree.insert(4, 4)

            assertEquals(4, tree.rootNode?.key)
            assertEquals(3, tree.rootNode?.left?.key)
            assertEquals(5, tree.rootNode?.right?.key)

            assertEquals(3, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `insert same key twice`() {
            tree.insert(1, 1)
            tree.insert(1, 2)
            assertEquals(1, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `insert many key-value pairs`() {
            values.forEach { tree.insert(it, it) }
            assertEquals(1000, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }
    }

    @Nested
    inner class FindTests {
        @Test
        fun `find a node by its key`() {
            values.forEach { tree.insert(it, it) }
            values.forEach { assertEquals(it, tree.find(it)) }
        }

        @Test
        fun `find by non-existing key`() {
            values.forEach { tree.insert(it, it) }
            assertNull(tree.find(-1))
        }
    }

    @Nested
    inner class DeletionTests {
        @Test
        fun `delete one key-value pair`() {
            tree.insert(1, 1)
            tree.remove(1)
            assertEquals(0, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `delete non-existent key`() {
            tree.insert(3, 3)
            tree.insert(4, 4)
            tree.insert(1, 1)
            tree.remove(-1)
            assertEquals(3, countNodes(tree.rootNode))
        }

        @Test
        fun `delete many key-value pairs`() {
            values.forEach { tree.insert(it, it) }
            values.take(500).forEach { tree.remove(it) }
            assertEquals(500, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }
    }
}
