package bst

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidRBTTest {

    @Test
    fun testInsert() {
        val tree = RedBlackTree<Int, String>()
        tree.insert(19, "S")
        tree.insert(5, "E")
        tree.insert(1, "A")
        tree.insert(18, "R")
        assertEquals("E", tree.find(5))
        assertEquals("A", tree.find(1))
        assertEquals(null, tree.find(10))
    }

    @Test
    fun testDelete() {
        val tree = RedBlackTree<Int, String>()
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
    }
}
