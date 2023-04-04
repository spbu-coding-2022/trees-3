package bst

interface Tree<K : Comparable<K>, V> {
    fun insert(key: K, value: V)
    fun remove(key: K)
    fun find(key: K): V?
}
