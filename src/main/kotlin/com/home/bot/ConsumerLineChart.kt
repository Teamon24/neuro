package com.home.bot

import com.home.bot.SourceImitation.SourceImitationConfigs.INTERVAL
import com.home.utils.functions.defaultFormatter
import com.home.utils.functions.printBounds
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart

data class Point<X,Y>(val x: X, val y: Y)

class CostConsumerLineChart: ConsumerLineChart<Long, Double>("Stock", 10 * 60 * 1_000, Long::minus, { l1, l2->l1>l2})

abstract class ConsumerLineChart<X: Number, Y:Number>(
    name: String,
    private val moveAfterValue: X,
    private val minus: (X, X) -> X,
    private val more: (X, X) -> Boolean,
    patternOfX: String = "",
    patternOfY: String = ""
) : SimpleConsumer<Point<X, Y>, Unit>()
{
    private var initTime: X? = null
    private val series = XYChart.Series<Number, Number>()
    private val xAxis = NumberAxis()
    private val yAxis = NumberAxis()

    var lineChart = LineChart<Number, Number>(xAxis, yAxis)

    init{
        if (patternOfX.isNotBlank()) xAxis.tickLabelFormatter = xAxis.defaultFormatter(patternOfX)
        if (patternOfY.isNotBlank()) yAxis.tickLabelFormatter = yAxis.defaultFormatter(patternOfY)
        xAxis.upperBound = moveAfterValue.toDouble()
        series.name = name
        lineChart.data.addAll(listOf(series))
    }

    override val acceptBody: (Point<X, Y>) -> Unit = {
        val millis = it.x
        if (initTime == null) {initTime = millis}
        val secondOfDay = minus(millis, initTime!!)

        val data: XYChart.Data<Number, Number> = XYChart.Data(secondOfDay, it.y)
        series.data.add(data)
        checkOrChangeBounds(xAxis, secondOfDay)
        printBounds(xAxis, yAxis)
    }

    private fun checkOrChangeBounds(axis: NumberAxis, secondOfDay: X) {
        if(axis.isAutoRanging) { axis.isAutoRanging = false }
        if( more(secondOfDay, moveAfterValue) ) {
            axis.lowerBound = axis.lowerBound + INTERVAL
            axis.upperBound = axis.upperBound + INTERVAL
            series.data.removeAt(0)
        }
    }
}