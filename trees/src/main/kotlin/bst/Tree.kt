package bst

/**
 * An interface for a binary search tree.
 * @param K the type of keys stored in the tree, must implement Comparable
 * @param V the type of values stored in the tree
 */

interface Tree<K : Comparable<K>, V> {

    /**
     * Inserts a key-value pair into the tree.
     * @param key the key to insert
     * @param value the value to associate with the key
     */

    fun insert(key: K, value: V)

    /**
     * Removes the key-value pair with the given key from the tree.
     * @param key the key of the pair to remove
     */

    fun remove(key: K)

    /**
     * Finds the value associated with the given key in the tree.
     * @param key the key to search for in the tree
     * @return the value associated with the given key if it exists, otherwise null
     */

    fun find(key: K): V?
}
