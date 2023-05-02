package app.view
import app.view.treeView.BinarySearchTreeView
import tornadofx.View
import tornadofx.vbox

/**
 * Represents the main view of the application.
 */

class MainView : View() {

    /**
     * Represents the binary search tree view of the application.
     */

    private val tree: BinarySearchTreeView by inject()

    /**
     * Represents the root node of the view.
     */

    override val root = vbox {
        add(tree)
    }
}
