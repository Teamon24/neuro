package com.home.ui.view

import com.home.neuro.WeightsMatrix
import com.home.utils.elements.Matrix2D
import javafx.scene.control.Label
import tornadofx.View
import tornadofx.gridpane
import tornadofx.insets

/**
 *
 */
class NeuronetStructureView<T>(weightsMatrix: WeightsMatrix<T>) : View() {

    override val root = gridpane {
        vgap = 10.0
        hgap = 10.0
        padding = insets(5)
        val weights: MutableList<out Matrix2D<T>> = weightsMatrix.weights
        var k = 0

        val column = ++k
        for (row in 1..weights[0].rows) {
            add(Label("$row"), column, row)
        }

        for (matrix2D in weights) {
            val column = ++k
            for (row in 1..matrix2D.cols) {
                add(Label("$row"), column, row)
            }
        }
    }

}