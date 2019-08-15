package com.home.ui.view

import com.home.neuro.NeuronNet
import tornadofx.*

class MainView : View("Hello TornadoFX") {

    val weights = NeuronNet(16, 2, 4).weights
    private val matricesViews = ArrayList<Matrix2DView<Double>>()

    override val root = hbox {
        this.add(NeuronetStructureView(weights))
        for (i in 0 until weights.sizes[0]) {
            val uiComponent = Matrix2DView(weights[i])
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