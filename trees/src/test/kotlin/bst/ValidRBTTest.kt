package bst

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidRBTTest {

    @Test
    fun testInsert() {
        val tree = RedBlackTree<Int, String>()
        tree.insert(1, "One")
        tree.insert(2, "Two")
        tree.insert(3, "Three")
        assertEquals(true, tree.find(1))
        assertEquals(true, tree.find(2))
        assertEquals(true, tree.find(3))
    }

    @Test
    fun testDelete() {
        val tree = RedBlackTree<Int, String>()
        tree.insert(1, "One")
        tree.insert(2, "Two")
        tree.insert(3, "Three")
        tree.remove(2)
        assertEquals(false, tree.find(2))
        assertEquals(true, tree.find(1))
        assertEquals(true, tree.find(3))
    }
}
