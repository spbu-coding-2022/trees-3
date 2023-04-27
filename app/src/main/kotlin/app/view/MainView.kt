package app.view
import app.view.treeView.BinarySearchTreeView
import tornadofx.*

class MainView: View(){
    private val tree = BinarySearchTreeView()
    override val root = borderpane {
        center{
            add(tree)
        }
        left{
            button("reset")
        }
    }
}