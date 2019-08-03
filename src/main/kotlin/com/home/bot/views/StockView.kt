package com.home.bot.views

import com.home.bot.*
import com.home.bot.utils.CSSUtils
import com.home.bot.utils.ThreadUtils
import com.home.bot.utils.costChart
import io.reactivex.subjects.PublishSubject
import tornadofx.View
import tornadofx.form
import tornadofx.scrollpane

class StockView() : View() {
    val costConsumerChart = CostConsumerChart()
    override val root = form {
        val scrollpane = scrollpane {
            costChart(costConsumerChart)
        }

        CSSUtils.addCss(scrollpane, "css/scroll-chart.css")
        CSSUtils.addCss(costConsumerChart.costChart, "css/stock-chart.css")
    }

    init {

        val externalApi = PublishSubject.create<Pair<Time, Cost>>()

        externalApi.subscribe(Bot(10.0, 5.0))
        externalApi.subscribe(costConsumerChart)

        ThreadUtils.printThread()
        tornadofx.runAsync {
            ThreadUtils.printThread()
            SourceImitation(externalApi).emmitting()
        }
    }
}