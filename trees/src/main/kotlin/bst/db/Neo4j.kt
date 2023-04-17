package bst.db

import bst.*
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.SessionFactory
import java.util.NoSuchElementException

class Neo4j(config: Configuration) {
    private val sessionFactory = SessionFactory(config, "bst")
    private val session = sessionFactory.openSession()

    fun saveTree(tree: AbstractBST<*, *, *>) {
        session.save(tree)
        session.query("MATCH (n: BinaryNode) WHERE NOT (n)--() DELETE (n)", mapOf<String, String>())
    }

    fun removeTree(name: String) {
        session.query("MATCH (n:${name})-[r *0..]->(m) DETACH DELETE m", mapOf<String, String>())
            ?: throw NoSuchElementException("No tree with that name has been found")
    }

    fun getTree(name: String) = session.queryForObject(
            AbstractBST::class.java, "MATCH (n:${name})-[r *1..]-(m) " +
            "RETURN n, r, m", mapOf("name" to name)) ?: throw NoSuchElementException("No tree with that name has been found")

    fun getNames() = session.query("MATCH (n: AbstractBST) return n", mapOf<String, String>())

}