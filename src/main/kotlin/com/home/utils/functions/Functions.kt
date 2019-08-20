package com.home.utils.functions

import com.home.bot.CostConsumerChart
import javafx.event.EventTarget
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import org.joda.time.DateTime
import tornadofx.attachTo

fun NumberAxis.defaultFormatter(pattern: String): NumberAxis.DefaultFormatter {
    return object : NumberAxis.DefaultFormatter(this) {
        override fun toString(value: Number) = DateTime(value.toLong()).toString(pattern)
    }
}

fun <K, V> LinkedHashMap<K, V>.put(pair: Pair<K, V>) { this[pair.first] = pair.second }

fun <K, V>       HashMap<K, V>.put(pair: Pair<K, V>) { this[pair.first] = pair.second }

fun printBounds(xAxis: NumberAxis, yAxis: NumberAxis) {
    println("xAxis.lower = ${xAxis.lowerBound} " +
            "xAxis.upper = ${xAxis.upperBound}\n" +
            "yAxis.lower = ${yAxis.lowerBound} " +
            "yAxis.upper = ${yAxis.upperBound}")
}

fun EventTarget.costChart(chart: CostConsumerChart,
                          title: String? = null,
                          op: LineChart<Number, Number>.() -> Unit = {}): LineChart<Number, Number>
{
    return chart.costChart.attachTo(this, op) { it.title = title }
}