package com.home.bot

import com.home.bot.SourceImitation.SourceImitationConfigs.INTERVAL
import com.home.bot.utils.Time.MINUTE
import com.home.bot.utils.defaultFormatter
import com.home.bot.utils.printBounds
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart


class CostConsumerChart : SimpleConsumer<Pair<Time, Cost>, Unit>() {

    companion object {
        private const val CHART_NAME = "Stock"
        private const val PATTERN = "mm:ss:SS"
        const val movingXBoundTime: Double = 3.0 * MINUTE
    }

    private var initTime: Long? = null

    private val series = XYChart.Series<Number, Number>()

    private val xAxis = NumberAxis()
    private val yAxis = NumberAxis()

    var costChart = LineChart<Number, Number>(xAxis, yAxis)

    init{

        xAxis.tickLabelFormatter = xAxis.defaultFormatter(PATTERN)
        xAxis.upperBound = movingXBoundTime
        series.name = CHART_NAME
        costChart.data.addAll(listOf(series))
    }



    override val acceptBody: (Pair<Time, Cost>) -> Unit = {

        val millis = it.first.value
        if (initTime == null) {initTime = millis}
        val secondOfDay = millis - initTime!!

        val data: XYChart.Data<Number, Number> = XYChart.Data(secondOfDay, it.second.value)
        series.data.add(data)
        checkOrChangeBounds(xAxis, secondOfDay)
        printBounds(xAxis, yAxis)
    }

    private fun checkOrChangeBounds(axis: NumberAxis, secondOfDay: Long) {
        if(axis.isAutoRanging) { axis.isAutoRanging = false }
        if( secondOfDay > movingXBoundTime ) {
            axis.lowerBound = axis.lowerBound + INTERVAL
            axis.upperBound = axis.upperBound + INTERVAL
            series.data.removeAt(0)
        }
    }
}