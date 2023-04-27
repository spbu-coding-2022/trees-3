package app

import app.view.MainView
import javafx.stage.Stage
import tornadofx.*

class MainApp: App(MainView::class){
    override fun start(stage: Stage) {
        with(stage){
            width = 600.0
            height = 400.0
        }
        super.start(stage)
    }
}

fun main() {
    launch<MainApp>()
}