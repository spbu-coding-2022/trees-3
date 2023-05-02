package app.controller

import bst.BSTree
import bst.db.controllers.SQLController
import bst.nodes.BSTNode
import javafx.collections.FXCollections.observableArrayList
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
 * The BSTController class is responsible for managing binary search trees.
 *
 * It extends the Controller class and provides additional functionality for working with binary search trees.
 */

class BSTController : Controller() {

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
     * Inserts a new node with the given key and value into the given binary search tree and redraws the tree on the given pane.
     *
     * @param tree      The binary search tree to insert the node into.
     * @param treePane  The pane where the tree is to be drawn.
     * @param key       The key of the new node to insert.
     * @param value     The value of the new node to insert.
     */

    fun insertNode(tree: BSTree<Int, String>, treePane: Pane, key: Int, value: String) {
        tree.insert(key, value)
        drawTree(tree, treePane)
    }

    /**
     * Clears the given binary search tree and removes all nodes from the given pane.
     *
     * @param tree      The binary search tree to clear.
     * @param treePane  The pane where the tree is to be drawn.
     */

    fun clearTree(tree: BSTree<Int, String>, treePane: Pane) {
        tree.clear()
        treePane.children.clear()
    }

    /**
     * Draws the given binary search tree on the given pane.
     *
     * @param tree The binary search tree to draw.
     * @param treePane  The pane where the tree is to be drawn.
     */

    fun drawTree(tree: BSTree<Int, String>, treePane: Pane) {
        treePane.children.clear()
        val root = tree.getRoot()
        if (root != null) {
            drawNode(root, treePane, treePane.width / 2.0, 50.0, treePane.width / 4.0)
        }
    }

    /**
     * Draws a binary search tree node on a given tree pane at a specific position with a given offset.
     *
     * @param node      The binary search tree node to draw.
     * @param treePane  The pane where the tree is to be drawn.
     * @param x         The x-coordinate of the node to draw.
     * @param y         The y-coordinate of the node to draw.
     * @param offsetX   The offset to apply to the x-coordinate of child nodes.
     */

    private fun drawNode(node: BSTNode<Int, String>, treePane: Pane, x: Double, y: Double, offsetX: Double) {
        // Create a circle to represent the node
        val circleRadius = 20.0
        val circle = Circle(circleRadius)
        circle.fill = Color.WHITE
        circle.stroke = Color.BLACK

        // Create a text label to display the node's key
        val nodeText = Text(node.key.toString())
        nodeText.boundsType = TextBoundsType.VISUAL

        // Scale the text label to fit inside the circle
        val scale =
            min(circleRadius * 1.3 / nodeText.boundsInLocal.width, circleRadius * 1.3 / nodeText.boundsInLocal.height)
        nodeText.scaleX = scale
        nodeText.scaleY = scale

        // Combine the circle and text label into a StackPane
        val nodeStackPane = StackPane(circle, nodeText)
        nodeStackPane.relocate(x - circleRadius, y - circleRadius)

        // Add a tooltip to the StackPane to display the node's value
        val tooltip = Tooltip("value: ${node.value}")
        Tooltip.install(nodeStackPane, tooltip)

        // Add the StackPane to the tree pane
        treePane.children.add(nodeStackPane)

        // If the node has a left child, draw it recursively
        if (node.left != null) {
            val leftX = x - offsetX
            val leftY = y + 50
            val leftLine = Line(x, y + circleRadius, leftX, leftY - circleRadius)
            treePane.children.add(leftLine)
            drawNode(node.left!!, treePane, leftX, leftY, offsetX / 2.0)
        }

        // If the node has a right child, draw it recursively
        if (node.right != null) {
            val rightX = x + offsetX
            val rightY = y + 50
            val rightLine = Line(x, y + circleRadius, rightX, rightY - circleRadius)
            treePane.children.add(rightLine)
            drawNode(node.right!!, treePane, rightX, rightY, offsetX / 2.0)
        }
    }

    /**
     * @return An ObservableList of tree names, or null if there are no trees in the database.
     */

    fun getTreesList(): ObservableList<String>? {
        // Creates a new SQLController object.
        val controller = SQLController()
        // Retrieves all tree names from the database.
        val treeNames = controller.getAllTrees()
        // Converts the list of tree names to an ObservableList.
        val values = observableArrayList<String>()
        treeNames.forEach {
            values.add(it)
        }
        // Returns the ObservableList of tree names.
        return values
    }

    /**
     * Retrieves a binary search tree with the given name from the database.
     *
     * @param name The name of the binary search tree to retrieve.
     * @return The binary search tree with the given name, or null if it does not exist in the database.
     */

    fun getTreeFromDB(name: String): BSTree<Int, String>? {
        // Creates a new SQLController object.
        val controller = SQLController()
        // Retrieves the binary search tree with the given name from the database.
        val tree = controller.getTree(name)
        // Returns the binary search tree.
        return tree
    }

    /**
     * Removes the binary search tree with the given name from the database.
     *
     * @param name The name of the binary search tree to remove.
     */

    fun deleteTreeFromDB(name: String) {
        // Creates a new SQLController object and removes the binary search tree with the given name from the database.
        SQLController().run {
            removeTree(name)
        }
    }

    /**
     * Saves a binary search tree to the database with the given name.
     *
     * @param tree The binary search tree to save.
     * @param treeName The name to give the saved binary search tree.
     */

    fun saveTree(tree: BSTree<Int, String>, treeName: String) {
        // Creates a new SQLController object and saves the binary search tree to the database with the given name.
        val controller = SQLController()
        controller.saveTree(tree, treeName)
    }

    /**
     * Removes the node with the given value from the binary search tree and redraws the tree on the given pane.
     *
     * @param value The value of the node to remove.
     * @param tree The binary search tree to remove the node from.
     * @param treePane The pane to redraw the tree on.
     */

    fun deleteNode(value: Int, tree: BSTree<Int, String>, treePane: Pane) {
        // Removes the node with the given value from the binary search tree.
        tree.remove(value)
        // Redraws the binary search tree on the given pane.
        drawTree(tree, treePane)
    }
}
