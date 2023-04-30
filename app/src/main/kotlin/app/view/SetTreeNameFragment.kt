package app.view

import bst.BSTree
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import app.controller.BSTController

class SetTreeNameFragment : Fragment() {
    private val name = SimpleStringProperty()
    private val controller = BSTController()
    override val root = vbox {
        field("Input tree name") {
            textfield(name)
        }
        button("save") {
            close()
//            tree.setName(name.value)
//            if (tree.getRoot() != null) {
//                controller.saveTree(tree)
//            }
        }
    }
}