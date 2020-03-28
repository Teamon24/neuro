package com.home.utils

import com.home.utils.functions.ifTrue
import java.util.*

/**
 *
 */
object Timer {
    val map= HashMap<String, Int> ()
    private const val template = "dd:MM:yyyy hh:mm:ss:SSSSSS"

    fun <Result> count(processName: String = "", toLog: Boolean = true, function: () -> Result): Pair<Result, Date> {
        toLog.ifTrue { println() }
        val name = countProcess(processName)
        val threadName = Thread.currentThread().name
        val start = Date()
        val result = function()
        val end = Date()
        val logTemplate2 = logTemplate(end, threadName, name)
        toLog.ifTrue { println("$logTemplate2 ended: ${DateTimeUtils.between(start, end).format("mm:ss:SSS")}") }
        return result to DateTimeUtils.between(start, end).toDate()
    }

    fun <Result> nanoCount(processName: String = "", toLog: Boolean = true, function: () -> Result): Pair<Result, Long> {
        toLog.ifTrue { println() }
        val name = countProcess(processName)
        val threadName = Thread.currentThread().name
        val start = System.nanoTime()
        val result = function()
        val end = System.nanoTime()
        val million = 1_000_000
        val endDate = Date(end / million)
        val logTemplate2 = logTemplate(endDate, threadName, name)
        val between = DateTimeUtils.between(Date(start/ million), endDate)
        toLog.ifTrue {
            println("$logTemplate2 ended: ${between.format("mm:ss:SSS")}")
        }
        return result to (end - start)
    }

    private fun logTemplate(date: Date, threadName: String?, name: String) =
        "[${date.format(template)}] $threadName: #$name"

    private fun countProcess(processName: String): String {
        val amount = map[processName]
        val newAmount = if (amount != null) amount + 1 else 1
        map[processName] = newAmount
        return "${processName}№$newAmount"
    }

    private fun Date.format(pattern: String) = DateTimeUtils.between(Date(0), this).format(pattern)
}

