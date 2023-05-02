package app

import app.view.MainView
import tornadofx.App
import tornadofx.launch
class MyApp : App(MainView::class)

fun main() {
    launch<MyApp>()
}
