package com.home.neuro.ui.styles

import com.home.neuro.ui.styles.Limits.MAX_ALPHA_VALUE
import com.home.neuro.ui.styles.Limits.MAX_COLOR_VALUE
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import tornadofx.MultiValue

object Limits {
    const val MAX_COLOR_VALUE = 255.0
    const val MAX_ALPHA_VALUE = 1.0
}

val Number.r get() = Red(this.toDouble())
val Number.g get() = Green(this.toDouble())
val Number.b get() = Blue(this.toDouble())
val Number.a get() = Alpha(this.toDouble())

class Red(value: Double): Clr(value, MAX_COLOR_VALUE)
class Green(value: Double): Clr(value, MAX_COLOR_VALUE)
class Blue(value: Double): Clr(value, MAX_COLOR_VALUE)
class Alpha(value: Double) : Clr(value, MAX_ALPHA_VALUE)

sealed class Clr(val value: Double, val limit: Double) {
    init {
        if (this.value > this.limit) throw IllegalStateException("value should be less than or equal to $limit")
    }
}

infix fun Color.set(color: Clr): Color {
    return when(color) {
        is Red -> Color(color.value, this.green, this.blue, this.opacity)
        is Green -> Color(this.red, color.value, this.blue, this.opacity)
        is Blue -> Color(this.red, this.green, color.value, this.opacity)
        is Alpha -> Color(this.red, this.green, this.blue, color.value)
    }
}

operator fun Color.plus(color: Clr) = operation(color, Double::plus)
operator fun Color.minus(color: Clr)= operation(color, Double::minus)

fun Color.operation(color: Clr, op: (Double, Double) -> Double): Color {
    return when(color) {
        is Red -> Color(limitClr(op(this.red, color.value)), this.green, this.blue, this.opacity)
        is Green -> Color(this.red, limitClr(op(this.red, color.value)), this.blue, this.opacity)
        is Blue -> Color(this.red, this.green, limitClr(op(this.red, color.value)), this.opacity)
        is Alpha -> Color(this.red, this.green, this.blue, limitOp(op(this.red, color.value)))
    }
}

fun limitOp(value: Double) = limit (0.0, 1.0, value)
fun limitClr(value: Double) = limit (0.0, 255.0, value)

fun Color.alpha(d: Double): Color {
    return Color(this.red, this.green, this.blue, d)
}

fun limit(min: Double, max: Double, value: Double): Double {
    if (value > max) return max
    if (value < min) return min
    return value
}


fun colors(vararg color: Color): MultiValue<Paint> = MultiValue(color)