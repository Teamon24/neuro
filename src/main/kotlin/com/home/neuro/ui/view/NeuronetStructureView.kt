package com.home.neuro.ui.view

import com.home.neuro.Weights
import com.home.neuro.ui.styles.Styles
import tornadofx.*

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
            add(layer(row), row, k)
        }

        val column = ++k
        for (row in 1..weights[0].rows) {
            add(neuron(row), column, row)
        }

        for (matrix2D in weights) {
            val column = ++k
            for (row in 1..matrix2D.cols) {
                add(neuron(row), column, row)
            }
        }
    }

    private fun layer(row: Int) = label { text = "layer: $row" }

    private fun neuron(row: Int) = label {
        text = "$row"
        addClass(Styles.neuron)
    }
}

