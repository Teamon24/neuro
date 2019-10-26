package com.home.neuro.ui.view

import com.home.neuro.ui.styles.Styles
import com.home.utils.Thrower
import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.type.Type
import javafx.geometry.Pos
import javafx.scene.control.Label
import tornadofx.*
import kotlin.reflect.KClass

class Matrix2DView<T>(matrixNum: Int, var matrix: Matrix2D<T>) : View() {

    val empty: (Label, Label) -> Label = { l1, l2 -> Label() }
    val labelType = object : Type<Label>({Label()}, empty, empty, empty){
        override fun random() = Label("empty")
        override fun clazz() = Label::class
    }

    var labels: Matrix2D<Label> = Matrix2D(labelType, matrix.rows, matrix.cols)

    fun update(matrix: Matrix2D<T>) {
        Thrower.throwIfUnequalSizes(this.matrix.base, matrix.base)
        this.matrix = matrix
        for (i in 0 until this.matrix.rows) {
            for (j in 0 until this.matrix.cols) {
                val label = labels[i][j]
                label.text = this.matrix[i][j].toString()
            }
        }
    }

    override val root = gridpane {
        addClass(Styles.matrix)
        val rows = matrix.rows
        val cols = matrix.cols
        vgap = 5.0
        padding = insets(5)
        val layer = matrixNum + 1
        label {
            addClass(Styles.header)
            add(headerLeft(layer), 0, 0)
            add(headerRight(layer + 1), 0, 0)
        }

        for (j in 1..cols) {
            add(neuronNumber(j), j, 0)
        }

        for (i in 1..rows) {
            add(neuronNumber(i), 0, i)
            for (j in 1..cols) {
                val weightValue = weightValue(matrix[i - 1][j - 1])
                labels[i-1][j-1] = weightValue
                add(weightValue, j, i)
            }
        }
    }

    private fun headerLeft(layer: Int) =
            label {
                addClass(Styles.headerLeft)
                text = "$layer"
            }

    private fun headerRight(layer: Int) =
        label {
            addClass(Styles.headerRight)
            text = "$layer"
        }


    private fun weightValue(value: T): Label {
        return label {
            addClass(Styles.weight)
            text = "$value"
            setMaxSize(60.0, 10.0)
            setPrefSize(60.0, 10.0)
        }
    }

    private fun neuronNumber(index: Int): Label {
        return label {
            addClass(Styles.weightNeuronNumber)
            text = "$index"
            alignment = Pos.TOP_LEFT
            setMaxSize(60.0, 10.0)
            setPrefSize(60.0, 10.0)
        }
    }

    override fun toString(): String {
        return "Matrix2DView(matrix=$matrix)"
    }


}
