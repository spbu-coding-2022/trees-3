package app

import app.view.MainView
import tornadofx.*

class MainApp: App(MainView::class)

fun main() {
    launch<MainApp>()
}