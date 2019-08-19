package com.home.ui.view

import com.home.ui.styles.Styles
import com.home.utils.elements.latest.Matrix2D
import javafx.geometry.Pos
import javafx.scene.control.Label
import tornadofx.*

class Matrix2DView<T>(matrixNum: Int, val matrix: Matrix2D<T>) : View() {

    //todo сделать Array2D
    //todo Array2D#add()
    //todo Array2D#add(i, j)
    //todo Array2D#replace(i, j)
    //todo Array2D#replace(i, j, transform: (T) -> T)
    var labels: ArrayList<ArrayList<Label>> = arrayListOf()

    override val root = gridpane {
        addClass(Styles.matrix)
        val rows = matrix.rows
        val cols = matrix.cols
        vgap = 5.0
        padding = insets(5)
        val layer = matrixNum + 1
        add(header(layer, layer + 1), 0, 0)
        for (j in 1..cols) {
            add(neuronNumber(j), j, 0)
        }

        for (i in 1..rows) {
            add(neuronNumber(i), 0, i)
            for (j in 1..cols) {
                add(weightValue(i, j), j, i)
            }
        }
    }

    private fun header(layer1: Int,
                       layer2: Int) =
        label {
            addClass(Styles.header)
            text = "[$layer1/$layer2]"
        }

    private fun weightValue(i: Int, j: Int): Label {
        return label {
            addClass(Styles.weight)
            text = "$i;$j"
            setMaxSize(60.0, 10.0)
            setPrefSize(60.0, 10.0)
        }
    }

    private fun neuronNumber(index: Int): Label {
        return label {
            addClass(Styles.header)
            text = "$index"
            alignment = Pos.TOP_LEFT
            setMaxSize(60.0, 10.0)
            setPrefSize(60.0, 10.0)
        }
    }
}
