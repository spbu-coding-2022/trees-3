package app.controller

import bst.AVLTree
import bst.db.controllers.JsonController
import bst.nodes.AVLNode
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
 * The AVLController class is responsible for managing AVL trees.
 *
 * It extends the Controller class and provides additional functionality for working with AVL trees.
 */

class AVLController : Controller() {

    /**
     * Returns a Boolean value indicating whether the given String can be converted to an Integer.
     *
     * @param s the String to be checked
     * @return true if the String can be converted to an Integer, false otherwise
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
     * Inserts a new node with the given key and value into the AVL Tree and redraws the tree in the tree pane.
     *
     * @param tree the AVL Tree to insert the node into
     * @param treePane the Pane where the AVL Tree is displayed
     * @param key the key of the new node to be inserted
     * @param value the value of the new node to be inserted
     */

    fun insertNode(tree: AVLTree<Int, String>, treePane: Pane, key: Int, value: String) {
        tree.insert(key, value)
        drawTree(tree, treePane)
    }

    /**
     * Clears all nodes from the AVL Tree and removes all nodes from the tree pane.
     *
     * @param tree the AVL Tree to be cleared
     * @param treePane the Pane where the AVL Tree is displayed
     */

    fun clearTree(tree: AVLTree<Int, String>, treePane: Pane) {
        tree.clear()
        treePane.children.clear()
    }

    /**
     * Draws the AVL Tree in the tree pane.
     *
     * @param tree the AVL Tree to be drawn
     * @param treePane the Pane where the AVL Tree is displayed
     */

    fun drawTree(tree: AVLTree<Int, String>, treePane: Pane) {
        treePane.children.clear()
        val root = tree.getRoot()
        if (root != null) {
            drawNode(root, treePane, treePane.width / 2.0, 50.0, treePane.width / 4.0)
        }
    }

    /**
     * Draws the given [AVLNode] in the [treePane] at the specified [x] and [y] coordinates with the given [offsetX].
     * @param node the [AVLNode] to be drawn
     * @param treePane the [Pane] in which the [AVLNode] is to be drawn
     * @param x the x-coordinate of the [AVLNode] in the [treePane]
     * @param y the y-coordinate of the [AVLNode] in the [treePane]
     * @param offsetX the horizontal offset from the [x] coordinate at which the [AVLNode] should be drawn
     */

    private fun drawNode(node: AVLNode<Int, String>, treePane: Pane, x: Double, y: Double, offsetX: Double) {
        val circleRadius = 20.0
        val circle = Circle(circleRadius)
        circle.fill = Color.WHITE
        circle.stroke = Color.BLACK

        val nodeText = Text(node.key.toString())
        nodeText.boundsType = TextBoundsType.VISUAL

        val scale =
            min(circleRadius * 1.3 / nodeText.boundsInLocal.width, circleRadius * 1.3 / nodeText.boundsInLocal.height)
        nodeText.scaleX = scale
        nodeText.scaleY = scale

        val nodeStackPane = StackPane(circle, nodeText)
        nodeStackPane.relocate(x - circleRadius, y - circleRadius)

        val tooltip = Tooltip("value: ${node.value}")
        Tooltip.install(nodeStackPane, tooltip)

        treePane.children.add(nodeStackPane)

        if (node.left != null) {
            val leftX = x - offsetX
            val leftY = y + 50
            val leftLine = Line(x, y + circleRadius, leftX, leftY - circleRadius)
            treePane.children.add(leftLine)
            drawNode(node.left!!, treePane, leftX, leftY, offsetX / 2.0)
        }

        if (node.right != null) {
            val rightX = x + offsetX
            val rightY = y + 50
            val rightLine = Line(x, y + circleRadius, rightX, rightY - circleRadius)
            treePane.children.add(rightLine)
            drawNode(node.right!!, treePane, rightX, rightY, offsetX / 2.0)
        }
    }

    /**
     * Retrieves a list of all tree names from the JSON controller.
     *
     * @return An observable list of tree names.
     */

    fun getTreesList(): ObservableList<String>? {
        val controller = JsonController()
        val treeNames = controller.getAllTrees()
        val values = FXCollections.observableArrayList<String>()
        treeNames.forEach {
            values.add(it)
        }
        return values
    }

    /**
     * Retrieves an AVL tree with the given name from the JSON controller.
     *
     * @param name The name of the tree to retrieve.
     * @return     The AVL tree with the given name, or null if it doesn't exist.
     */

    fun getTreeFromJson(name: String): AVLTree<Int, String>? {
        val controller = JsonController()
        return controller.getTree(name)
    }

    /**
     * Removes the AVL tree with the given name from the JSON controller.
     *
     * @param name The name of the tree to remove.
     */

    fun deleteTreeFromDB(name: String) {
        JsonController().run {
            removeTree(name)
        }
    }

    /**
     * Saves the given AVL tree to the JSON controller with the given name.
     *
     * @param tree      The AVL tree to save.
     * @param treeName  The name to use when saving the tree.
     */

    fun saveTree(tree: AVLTree<Int, String>, treeName: String) {
        val controller = JsonController()
        controller.saveTree(tree, treeName)
    }

    /**
     * Removes a node with the given value from the given AVL tree and redraws the tree on the given pane.
     *
     * @param value     The value of the node to remove.
     * @param tree      The AVL tree to remove the node from.
     * @param treePane  The pane where the tree is to be drawn.
     */

    fun deleteNode(value: Int, tree: AVLTree<Int, String>, treePane: Pane) {
        tree.remove(value)
        drawTree(tree, treePane)
    }
}
