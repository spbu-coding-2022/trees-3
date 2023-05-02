package bst

import bst.nodes.BSTNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BstTest {
    private fun <K : Comparable<K>, V> isBst(root: BSTNode<K, V>?): Boolean {
        if (root == null) {
            return true
        }
        val left = root.left
        val right = root.right
        if ((left != null && left.key >= root.key) || (right != null && right.key <= root.key)) {
            return false
        }
        return isBst(left) && isBst(right)
    }

    private fun <K : Comparable<K>, V> countNodes(node: BSTNode<K, V>?): Int {
        if (node == null) return 0
        return countNodes(node.left) + countNodes(node.right) + 1
    }

    private lateinit var tree: BSTree<Int, Int>

    @BeforeEach
    fun initializeObjects() {
        tree = BSTree()
    }

    @Test
    fun `validate tree if empty`() {
        assertNull(tree.rootNode)
        assertEquals(0, countNodes(tree.rootNode))
        assertTrue(isBst(tree.rootNode))
    }

    @Test
    fun `verify invariants after each action`() {
        val values = IntArray(1000) { it + 1 }
        values.shuffle()
        values.forEach {
            tree.insert(it, it)
            assertTrue(isBst(tree.rootNode))
        }
        values.take(500).forEach {
            tree.remove(it)
            assertTrue(isBst(tree.rootNode))
        }
        values.takeLast(500).forEach {
            assertNotEquals(null, tree.find(it))
        }
    }
}
