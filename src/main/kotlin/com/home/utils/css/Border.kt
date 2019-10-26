package com.home.utils.css

import javafx.scene.paint.Color
import tornadofx.*

class Border {
    var radius = 0.px
    var color = Companion.INITIAL
    var width = 0.px

    fun radius(radius: Dimension<Dimension.LinearUnits>): Border {
        this.radius = radius
        return this
    }

    fun color(color: Color): Border {
        this.color = color
        return this
    }

    fun width(width: Dimension<Dimension.LinearUnits>): Border {
        this.width = width
        return this
    }

    fun get(): CssSelectionBlock {
        return mixin {
            if (width != 0.px) borderWidth += box(width)
            if (color != INITIAL) borderColor += box(color)
            if (radius != 0.px) borderRadius += box(radius)
        }
    }

    companion object {
        val INITIAL = c(0.0, 0.0, 0.0, 0.0)
    }
}