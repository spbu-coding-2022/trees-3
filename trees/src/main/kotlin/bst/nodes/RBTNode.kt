package bst.nodes

/**
 * Represents a node in a Red-Black Tree.
 *
 * @param K the type of the key.
 * @param V the type of the value.
 * @param key the key associated with this node.
 * @param value the value associated with this node.
 * @param color the color of the node.
 * @param BinaryNode the parent class representing a binary node.
 */

class RBTNode<K : Comparable<K>, V>(
    key: K,
    value: V,
    var color: Color = Color.RED
) : BinaryNode<K, V, RBTNode<K, V>>(key, value) {

    /**
     * Returns the child node of this node based on the given flag.
     *
     * @param f a flag indicating whether to return the right child (true) or the left child (false).
     * @return the child node of this node based on the given flag.
     */
    internal fun child(f: Boolean) = if (f) right else left

    /**
     * Represents the color of the node.
     */
    enum class Color {
        RED, BLACK
    }
}
