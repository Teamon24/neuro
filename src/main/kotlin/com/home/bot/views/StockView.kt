package com.home.bot.views

import com.home.bot.*
import com.home.utils.css.CSSUtils
import com.home.utils.ThreadUtils
import com.home.utils.functions.costChart
import io.reactivex.subjects.PublishSubject
import tornadofx.*

fun main() {
    launch<StockApp>()
}

class StockApp: App(StockView::class)

class StockView() : View() {
    private val costChart = CostConsumerLineChart()
    override val root = form {
        val scrollpane = scrollpane {
            costChart(costChart)
        }

        CSSUtils.addCss(scrollpane, "css/scroll-chart.css")
        CSSUtils.addCss(costChart.lineChart, "css/stock-chart.css")
    }

    init {

        val externalApi = PublishSubject.create<Point<Long, Double>>()

        externalApi.subscribe(Bot(10.0, 5.0))
        externalApi.subscribe(costChart)

        ThreadUtils.printThread()
        tornadofx.runAsync {
            ThreadUtils.printThread()
            SourceImitation(externalApi).emmitting()
        }
    }
}