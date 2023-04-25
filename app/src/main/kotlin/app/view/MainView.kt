package app.view

import tornadofx.*

class MainView: View("Trees") {
    override val root = vbox {
        button("Hello, World!")
        label("Waiting")
    }
}