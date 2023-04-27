package app.view.treeView
import tornadofx.*
class AVLTreeView: View() {
    override val root = vbox {
        button("Binary Search Tree") {
            action {
                replaceWith<BinarySearchTreeView>()
            }
        }
        button("Red Black Tree") {
            action {
                replaceWith<RedBlackTreeView>()
            }
        }
    }
}