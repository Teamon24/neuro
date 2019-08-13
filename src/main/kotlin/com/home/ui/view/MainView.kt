package com.home.ui.view

import com.home.neuro.Neurons
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    val weightsMatrix = Neurons(16, 2, 4).weightsMatrix
    private val matricesViews = ArrayList<Matrix2DView<Double>>()

    override val root = hbox {
        this.add(NeuronetStructureView(weightsMatrix))
        for (i in 0 until weightsMatrix.weights.size) {
            val uiComponent = Matrix2DView(weightsMatrix[i])
            matricesViews.add(uiComponent)
            this.add(uiComponent)
        }

        tornadofx.runAsync {
            for (i in 1..10)
            Thread.sleep(2000)
            matricesViews[0]
        } ui {

        }
    }
}