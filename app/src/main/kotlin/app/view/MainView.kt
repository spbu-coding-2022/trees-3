package app.view
import app.view.treeView.BinarySearchTreeView
import tornadofx.*

class MainView : View() {
    private val tree: BinarySearchTreeView by inject()
    override val root = vbox {
        add(tree)
    }
}
