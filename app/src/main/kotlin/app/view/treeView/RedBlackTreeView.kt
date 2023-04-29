package app.view.treeView
import app.view.treeView.AVLTreeView
import app.view.treeView.BinarySearchTreeView
import tornadofx.*

class RedBlackTreeView: View() {
    override val root = vbox {
        button("AVL Tree") {
            action {
                replaceWith(AVLTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))

            }
        }

        button("Binary Search Tree") {
            action {
                replaceWith(BinarySearchTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))

            }
        }
    }
}