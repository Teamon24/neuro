package com.home.neuro.ui.styles

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.*

class Styles : Stylesheet() {

    companion object {
        val main by cssclass()
        val separator by cssclass()
        val matrix by cssclass()
        val neuron by cssclass()
        val weight by cssclass()
        val header by cssclass()
    }

    init {

        val screenWidth = 1920.px
        val pxes = 30.px

        val sizes = mixin {
            minWidth = screenWidth
        }

        val min = mixin {
            minHeight = pxes
        }

        val defaultBorder = border(5.px, 2.px, c(0, 0, 0, 0.8))

        main {
            +sizes
            label {
                fontSize = 20.px
            }
        }

        val faintGray = c(230, 230, 230, 0.5)
        val blue = c(29, 44, 245, 0.5)
        val orange = c(225, 92, 9)
        val color = orange

        separator {
            +sizes
            minHeight = 100.px
            backgroundColor = colors(color.alpha(0.8))
            +border(0.px, 0.px, color)
        }

        matrix {
            vgap = 3.px
            hgap = 3.px
            prefWidth = 100.percent
            alignment = Pos.CENTER
            padding = box(30.px)
        }

        weight {
            backgroundColor = colors(faintGray)
            minWidth = 100.px
            +defaultBorder
        }

        header {
            alignment = Pos.CENTER
            minWidth = 100.px
            backgroundColor = colors(color.alpha(0.1))
            +border(2.px, 3.px, color)
        }

        neuron {
            +min
            minWidth = 100.px
            alignment = Pos.CENTER
            +defaultBorder
        }
    }

    private fun border(
        radius: Dimension<Dimension.LinearUnits>,
        width: Dimension<Dimension.LinearUnits>,
        color: Color
    ): CssSelectionBlock {
        return mixin {
            borderRadius += box(radius)
            borderColor += box(color)
            borderWidth += box(width)
        }
    }

    fun Color.alpha(d: Double): Color {
        return Color(this.red, this.green, this.blue, d)
    }

    private fun colors(faintGray: Color): MultiValue<Paint> = MultiValue(arrayOf(faintGray))
}