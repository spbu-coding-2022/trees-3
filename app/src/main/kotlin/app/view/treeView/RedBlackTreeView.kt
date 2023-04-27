package app.view.treeView
import app.view.treeView.AVLTreeView
import app.view.treeView.BinarySearchTreeView
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.vbox

class RedBlackTreeView: View() {
    override val root = vbox {
        button("AVL Tree") {
            action {
                replaceWith<AVLTreeView>()
            }
        }

        button("Binary Search Tree") {
            action {
                replaceWith<BinarySearchTreeView>()
            }
        }
    }
}