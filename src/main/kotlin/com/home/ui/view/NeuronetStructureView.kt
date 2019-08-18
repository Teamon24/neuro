package com.home.ui.view

import com.home.neuro.Weights
import javafx.scene.control.Label
import tornadofx.View
import tornadofx.gridpane
import tornadofx.insets

/**
 *
 */
class NeuronetStructureView(weights: Weights) : View() {

    override val root = gridpane {
        vgap = 10.0
        hgap = 10.0
        padding = insets(5)
        val weights = weights
        var k = 0


        for (row in 1..weights.size + 1) {
            add(Label("layer: $row"), row, k)
        }

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

