package app.view.treeView
import tornadofx.*
import app.controller.BSTController
import app.view.AdditionErrorFragment
import bst.BSTree
import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.stage.StageStyle
import javafx.util.Callback

class BinarySearchTreeView : View() {
    private val controller: BSTController by inject()
    private var tree = BSTree<Int, String>()
    private val treePane = Pane()
    private val key = SimpleStringProperty()
    private val value = SimpleStringProperty()
    private var trees = controller.getTreesList()
    var selectedItem: String? by singleAssign()

    override val root = vbox {
        hbox {
            combobox<String> {
                this@BinarySearchTreeView.trees?.let { items.addAll(it) }
                selectionModel.selectedItemProperty().addListener { _, _, newValue ->
                    this@BinarySearchTreeView.selectedItem = newValue
                }
            }

            button("Select") {
                action {
                    println("Selected item: $selectedItem")
                    val loadedTree = selectedItem?.let { controller.getTreeFromDB(it) }
                    if (loadedTree != null) {
                        tree = loadedTree
                        controller.drawTree(tree, treePane)
                    }
                }
            }

            button("Clear") {
                action {
                    controller.clearTree(tree, treePane)

                }
            }
            form {

                fieldset {
                    field("Key input") {
                        textfield(key)
                    }
                    field("Value input") {
                        textfield(value)
                    }

                    button("Add Node") {
                        action {
                            if (key.value != null && value.value != null) {
                                if (controller.isNumeric(key.value)) {
                                    controller.insertNode(tree, treePane, key.value.toInt(), value.value)
                                } else {
                                    find<AdditionErrorFragment>().openModal(stageStyle = StageStyle.UTILITY)
                                }
                            } else {
                                find<AdditionErrorFragment>().openModal(stageStyle = StageStyle.UTILITY)
                            }
                            key.value = ""
                            value.value = ""
                        }
                    }
                }
            }
        }
        button("AVL Tree") {
            action {
                replaceWith(AVLTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }
        button("Red Black Tree") {
            action {
                replaceWith(RedBlackTreeView::class, ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
            }
        }
        this += treePane
        treePane.apply {
            minWidth = 600.0
            minHeight = 400.0
            style = "-fx-border-color: black;"
        }
    }
}
