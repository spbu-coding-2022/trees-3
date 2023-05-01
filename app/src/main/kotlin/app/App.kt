package app

import app.view.MainView
import tornadofx.*
class MyApp : App(MainView::class)

fun main() {
    launch<MyApp>()
}
