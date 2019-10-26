package com.home.neuro.ui.styles

import com.home.utils.css.Border
import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.paint.Color.*
import tornadofx.*

class Styles : Stylesheet() {

    companion object {
        val APP_COLOR = c(225, 92, 9)

        val screenWidth = 1920.px
        val screenHeight = 1920.px

        var dataSetUIImageSize = 0.px
        var tabAmount = 0

        val main by cssclass()
        val separator by cssclass()
        val matrix by cssclass()

        val neuronNumber by cssclass()
        val weight by cssclass()
        val weightNeuronNumber by cssclass()

        val headerLeft by cssclass()
        val headerRight by cssclass()
        val header by cssclass()
        val dataSetGroup by cssclass()

        val errorChart by cssclass()
    }

    init {
        val faintGray = c(230, 230, 230, 0.8)
        val sizes = mixin {
            minWidth = screenWidth
        }

        val neuronSize = mixin {
            minHeight = 40.px
            minWidth = 40.px
        }

        val cellMinHeight = 25.px
        val cellMinWidth = 55.px

        val cellSize = mixin {
            minHeight = cellMinHeight
            minWidth = cellMinWidth
        }


        val dataSetUIImageSize = mixin {
            prefWidth = dataSetUIImageSize
            prefHeight = dataSetUIImageSize
        }

        val defaultBorder =
            border()
            .width(5.px)
            .radius(2.px)
            .get()

        val circleBorder =
            border()
                .radius(Int.MAX_VALUE.px)
                .width(2.px)
                .color(GRAY - 0.1.a).get()

        main {
            +sizes
            label {
                fontSize = 13.px
            }
            tabMinWidth = screenWidth / tabAmount / 2
            tabMinHeight = 2.em
        }

        errorChart {
            minWidth = screenWidth / 4
            minHeight = screenHeight / 4

            maxWidth = screenWidth / 4
            maxHeight = screenHeight / 4

            legend

        }

        matrix {
            vgap = 3.px
            hgap = 3.px
            prefWidth = 100.percent
            alignment = Pos.TOP_LEFT
            padding = box(30.px)
        }

        weight {
            +cellSize
            +defaultBorder
            alignment = Pos.CENTER
            +header(faintGray)
        }

        weightNeuronNumber {
            +cellSize
            alignment = Pos.CENTER
            +header(APP_COLOR)
        }

        header {
            minWidth = cellMinWidth + 10.px
            minHeight = cellMinHeight + 10.px
        }

        headerLeft {
            +cellSize
            alignment = Pos.CENTER_LEFT
            +header(APP_COLOR)
            shape = "M 700 300 L 100 100 L 100 300 L 700 300"
        }

        headerRight {
            +cellSize
            alignment = Pos.BASELINE_RIGHT
            +header(APP_COLOR)
            padding = box(2.px)
            shape = "M 100 300 L 350 300 L 350 400 L 100 300"
        }

        neuronNumber {
            +neuronSize
            alignment = Pos.CENTER
            +circleBorder
        }

        dataSetGroup {
            +dataSetUIImageSize
            alignment = Pos.CENTER
            +header(APP_COLOR)
        }
    }

    private fun header(color: Color): CssSelectionBlock {
        return mixin {
            backgroundColor += color set 0.1.a
            +border().radius(1.px).width(1.px).color(color set 0.15.a).get()
        }
    }

    private fun border(): Border {
        return Border()
    }



}


