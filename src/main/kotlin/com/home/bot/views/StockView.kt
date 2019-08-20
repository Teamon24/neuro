package com.home.bot.views

import com.home.bot.*
import com.home.utils.CSSUtils
import com.home.utils.ThreadUtils
import com.home.utils.functions.costChart
import io.reactivex.subjects.PublishSubject
import tornadofx.View
import tornadofx.form
import tornadofx.scrollpane

class StockView() : View() {
    val costChart = CostConsumerChart()
    override val root = form {
        val scrollpane = scrollpane {
            costChart(costChart)
        }

        CSSUtils.addCss(scrollpane, "css/scroll-chart.css")
        CSSUtils.addCss(costChart.costChart, "css/stock-chart.css")
    }

    init {

        val externalApi = PublishSubject.create<Pair<Time, Cost>>()

        externalApi.subscribe(Bot(10.0, 5.0))
        externalApi.subscribe(costChart)

        ThreadUtils.printThread()
        tornadofx.runAsync {
            ThreadUtils.printThread()
            SourceImitation(externalApi).emmitting()
        }
    }
}