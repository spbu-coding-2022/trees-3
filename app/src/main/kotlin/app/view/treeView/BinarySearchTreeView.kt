package app.view.treeView
import tornadofx.*

class BinarySearchTreeView: View() {
    override val root = vbox {
        button("AVL Tree") {
            action {
                replaceWith<AVLTreeView>()
            }
        }
        button("Red Black Tree") {
            action {
                replaceWith<RedBlackTreeView>()
            }
        }
    }
}
