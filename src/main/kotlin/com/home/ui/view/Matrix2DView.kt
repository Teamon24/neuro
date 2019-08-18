package com.home.ui.view

import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.latest.Matrix2D
import javafx.geometry.Pos
import javafx.scene.control.Label
import tornadofx.View
import tornadofx.gridpane
import tornadofx.insets

class Matrix2DView<T>(val matrix: Matrix2D<T>) : View() {

    //todo сделать Array2D
    //todo Array2D#add()
    //todo Array2D#add(i, j)
    //todo Array2D#replace(i, j)
    //todo Array2D#replace(i, j, transform: (T) -> T)
    var labels: Array<Array<Label>> = arrayOf()


    override val root = gridpane {

        val rows = matrix.rows
        val cols = matrix.cols
        vgap = 5.0
        padding = insets(5)
        for (i in 1..rows) {
            add(label(i), 0, i)
            for (j in 1..cols) {
                add(label(i, j), j, i)
            }
        }
    }

    private fun label(i: Int, j: Int): Label {
        val label = Label("($i;$j)")
        label.setMaxSize(60.0, 10.0)
        label.setPrefSize(60.0, 10.0)
        return label
    }

    private fun label(row: Int): Label {
        val label = Label("$row:")
        label.setMaxSize(60.0, 10.0)
        label.setPrefSize(60.0, 10.0)
        label.alignment = Pos.TOP_LEFT
        return label
    }
}
