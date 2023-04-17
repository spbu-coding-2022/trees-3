package bst

import bst.nodes.BSTNode
import org.neo4j.ogm.annotation.Labels
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.session.query

class BSTree<K: Comparable<K>, V> : AbstractBST<K, V, BSTNode<K, V>>() {
    override fun initNode(key: K, value: V): BSTNode<K, V> = BSTNode(key, value)
}
