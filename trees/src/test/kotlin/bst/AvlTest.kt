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
    private fun<K : Comparable<K>, V> isAvl(node: AVLNode<K, V>?): Boolean {
        if (node == null) return true
        return isAvl(node.left) &&
            isAvl(node.right) &&
            (subtreeHeight(node.left) - subtreeHeight(node.right) in -1..1)
    }
    private fun<K : Comparable<K>, V> subtreeHeight(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return max(subtreeHeight(node.left), subtreeHeight(node.right)) + 1
    }
    private fun<K : Comparable<K>, V> countNodes(node: AVLNode<K, V>?): Int {
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
    fun `Empty tree`() {
        assertNull(tree.rootNode)
        assertEquals(0, countNodes(tree.rootNode))
        assertTrue(isAvl(tree.rootNode))
    }

    @Nested
    inner class InsertionTests {
        @Test
        fun `Simple insertion`() {
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
        fun `Left-left rotation case`() {
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
        fun `Left-right rotation case`() {
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
        fun `Right-right rotation case`() {
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
        fun `Right-left rotation case`() {
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
        fun `Same key inserted twice`() {
            tree.insert(1, 1)
            tree.insert(1, 2)
            assertEquals(1, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `Multiple insertions`() {
            values.forEach { tree.insert(it, it) }
            assertEquals(1000, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }
    }

    @Nested
    inner class FindTests {
        @Test
        fun `Find test`() {
            values.forEach { tree.insert(it, it) }
            values.forEach { assertEquals(it, tree.find(it)) }
        }

        @Test
        fun `Find by non-existing key`() {
            values.forEach { tree.insert(it, it) }
            assertNull(tree.find(-1))
        }
    }

    @Nested
    inner class DeletionTests {
        @Test
        fun `Single deletion`() {
            tree.insert(1, 1)
            tree.remove(1)
            assertEquals(0, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }

        @Test
        fun `Non-existent key deletion`() {
            tree.insert(3, 3)
            tree.insert(4, 4)
            tree.insert(1, 1)
            tree.remove(-1)
            assertEquals(3, countNodes(tree.rootNode))
        }

        @Test
        fun `Multiple deletions`() {
            values.forEach { tree.insert(it, it) }
            values.take(500).forEach { tree.remove(it) }
            assertEquals(500, countNodes(tree.rootNode))
            assertTrue(isAvl(tree.rootNode))
        }
    }
}
