package com.home.bot.css

import javafx.scene.paint.Paint
import tornadofx.*

/**
 *
 */
class LineChartStyle : Stylesheet() {
    companion object {
        val chart by cssclass()
        val fit by cssclass()
    }

    init {
        fit {
            fitToHeight = true
            fitToWidth = true
        }

        chart {
            backgroundColor = MultiValue(arrayOf(Paint.valueOf("#e16ff")))
            chartSeriesLine {
//                stroke = c("#e16ff")
                strokeWidth = 1.5.px
            }

            chartLineSymbol {
                //                borderRadius = box(1.0.px)
                padding = box(1.0.px)
            }
        }
    }


}
