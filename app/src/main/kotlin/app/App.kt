package app

import app.view.MainView
import tornadofx.App
import tornadofx.launch

/**
 * Represents the main application class.
 */

class MyApp : App(MainView::class)

/**
 * The main entry point of the application.
 */

fun main() {
    launch<MyApp>()
}
