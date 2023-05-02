package app.controller

import bst.RedBlackTree
import bst.db.controllers.Neo4jController
import bst.nodes.RBTNode
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Tooltip
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.text.Text
import javafx.scene.text.TextBoundsType
import tornadofx.Controller
import kotlin.math.min

/**
 * The RBTController class is responsible for managing red black trees.
 *
 * It extends the Controller class and provides additional functionality for working with RBT trees.
 */

class RBTController : Controller() {

    /**
     * Determines whether a given string can be parsed as an integer.
     *
     * @param s The string to check.
     * @return  True if the string can be parsed as an integer, false otherwise.
     */

    fun isNumeric(s: String): Boolean {
        return try {
            s.toInt()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * Inserts a new node with the given key and value into the Red-Black Tree and redraws the tree.
     * @param tree The Red-Black Tree to insert the node into.
     * @param treePane The Pane to draw the updated tree on.
     * @param key The key of the new node to insert.
     * @param value The value of the new node to insert.
     */

    fun insertNode(tree: RedBlackTree<Int, String>, treePane: Pane, key: Int, value: String) {
        tree.insert(key, value)
        drawTree(tree, treePane)
    }

    /**
     * Clears the Red-Black Tree and removes all nodes from the given Pane.
     * @param tree The Red-Black Tree to clear.
     * @param treePane The Pane to remove all nodes from.
     */

    fun clearTree(tree: RedBlackTree<Int, String>, treePane: Pane) {
        tree.clear()
        treePane.children.clear()
    }

    /**
     * Draws the Red-Black Tree on the given Pane.
     * @param tree The Red-Black Tree to draw.
     * @param treePane The Pane to draw the tree on.
     * @throws NullPointerException if the root of the tree is null.
     */

    fun drawTree(tree: RedBlackTree<Int, String>, treePane: Pane) {
        treePane.children.clear()
        val root = tree.getRoot()
        if (root != null) {
            drawNode(root, treePane, treePane.width / 2.0, 50.0, treePane.width / 4.0)
        } else {
            throw NullPointerException("Root of the tree is null.")
        }
    }

    /**
     * Draws a node of the Red-Black Tree on the given Pane, including a circle with the node's key and a tooltip with its value.
     * If the node has a left child or a right child, it also draws a line to the child and recursively calls this function on the child.
     * @param node The RBTNode to draw.
     * @param treePane The Pane to draw the node on.
     * @param x The x-coordinate of the center of the circle representing the node.
     * @param y The y-coordinate of the center of the circle representing the node.
     * @param offsetX The horizontal distance between the node and its child nodes.
     */

    private fun drawNode(node: RBTNode<Int, String>, treePane: Pane, x: Double, y: Double, offsetX: Double) {
        // Create circle with node key
        val circleRadius = 20.0
        val circle = Circle(circleRadius)
        circle.fill = if (node.color == RBTNode.Color.RED) Color.RED else Color.BLACK
        circle.stroke = Color.BLACK

        // Create text with node key
        val nodeText = Text(node.key.toString())
        nodeText.fill = if (circle.fill == Color.RED) Color.BLACK else Color.WHITE
        nodeText.boundsType = TextBoundsType.VISUAL

        // Scale text to fit inside circle
        val scale = min(circleRadius * 1.3 / nodeText.boundsInLocal.width, circleRadius * 1.3 / nodeText.boundsInLocal.height)
        nodeText.scaleX = scale
        nodeText.scaleY = scale

        // Create StackPane with circle and text, and set its position
        val nodeStackPane = StackPane(circle, nodeText)
        nodeStackPane.relocate(x - circleRadius, y - circleRadius)

        // Add tooltip with node value to StackPane
        val tooltip = Tooltip("value: ${node.value}")
        Tooltip.install(nodeStackPane, tooltip)

        // Add StackPane to treePane
        treePane.children.add(nodeStackPane)

        // Draw line to left child and recursively call drawNode on it
        if (node.left != null) {
            val leftX = x - offsetX
            val leftY = y + 50
            val leftLine = Line(x, y + circleRadius, leftX, leftY - circleRadius)
            treePane.children.add(leftLine)
            drawNode(node.left!!, treePane, leftX, leftY, offsetX / 2.0)
        }

        // Draw line to right child and recursively call drawNode on it
        if (node.right != null) {
            val rightX = x + offsetX
            val rightY = y + 50
            val rightLine = Line(x, y + circleRadius, rightX, rightY - circleRadius)
            treePane.children.add(rightLine)
            drawNode(node.right!!, treePane, rightX, rightY, offsetX / 2.0)
        }
    }

    /**
     * Gets a list of names of Red-Black Trees stored in a Neo4j database and returns it as an ObservableList of Strings.
     * @return An ObservableList of Strings containing the names of Red-Black Trees stored in the Neo4j database.
     */

    fun getTreesList(): ObservableList<String>? {
        val controller = Neo4jController()
        val treeNames = controller.getNames()
        val values = FXCollections.observableArrayList<String>()
        treeNames.forEach {
            values.add(it)
        }
        return values
    }

    /**
     * Retrieves a Red-Black Tree with the given name from a Neo4j database and returns it.
     * @param name The name of the Red-Black Tree to retrieve.
     * @return The Red-Black Tree with the given name, or null if it does not exist in the database.
     */

    fun getTreeFromNeo4j(name: String): RedBlackTree<Int, String>? {
        val controller = Neo4jController()
        return controller.getTree(name)
    }

    /**
     * Deletes a Red-Black Tree with the given name from a Neo4j database.
     * @param name The name of the Red-Black Tree to delete.
     */

    fun deleteTreeFromDB(name: String) {
        Neo4jController().run {
            removeTree(name)
        }
    }

    /**
     * Saves a Red-Black Tree to a Neo4j database with the given name.
     * @param tree The Red-Black Tree to save.
     * @param treeName The name to give the Red-Black Tree in the database.
     */

    fun saveTree(tree: RedBlackTree<Int, String>, treeName: String) {
        val controller = Neo4jController()
        controller.saveTree(tree, treeName)
    }

    /**
     * Removes a node with the given value from a Red-Black Tree, redraws the tree, and updates the given Pane.
     * @param value The value of the node to remove.
     * @param tree The Red-Black Tree to remove the node from.
     * @param treePane The Pane to draw the updated tree on.
     */

    fun deleteNode(value: Int, tree: RedBlackTree<Int, String>, treePane: Pane) {
        tree.remove(value)
        drawTree(tree, treePane)
    }
}
