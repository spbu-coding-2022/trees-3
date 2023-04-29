package app.view.treeView
import tornadofx.*
class AVLTreeView: View() {
    override val root = vbox {
        button("Binary Search Tree") {
            action {
                replaceWith(BinarySearchTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }
        button("Red Black Tree") {
            action {
                replaceWith(RedBlackTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }
    }
}