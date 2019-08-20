package com.home.bot

import com.home.utils.CSSUtils
import com.home.utils.ThreadUtils
import io.reactivex.subjects.PublishSubject
import javafx.application.Application
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.ScrollPane
import javafx.stage.Stage
import tornadofx.add
import tornadofx.launch
import tornadofx.runAsync

fun main() {
    launch<Main>()
}

class Main : Application() {
    override fun start(primaryStage: Stage?) {
        val costChart = CostConsumerChart()
        val externalApi = PublishSubject.create<Pair<Time, Cost>>()

        externalApi.subscribe(Bot(10.0, 5.0))
        externalApi.subscribe(costChart)

        val chart = costChart.costChart

//        chart.addClass(LineChartStyle.chart)
        CSSUtils.addCss(chart, "css/stock-chart.css")
        val scrollPane = scrollPane { chart }

        CSSUtils.addCss(scrollPane, "css/stock-chart.css")
        primaryStage!!.scene = Scene(scrollPane)
        primaryStage.show()
        ThreadUtils.printThread()

        runAsync {
            ThreadUtils.printThread()
            SourceImitation(externalApi).emmitting()
        }
    }
}

fun scrollPane(root: () -> Node): ScrollPane {
    val scrollPane = ScrollPane()
    scrollPane.add(root())
    return scrollPane
}


