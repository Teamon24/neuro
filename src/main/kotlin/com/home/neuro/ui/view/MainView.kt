package com.home.neuro.ui.view

import com.home.utils.CSSUtils
import com.home.neuro.NeuronNet
import com.home.neuro.ui.styles.Styles
import com.home.utils.functions.i
import com.home.utils.functions.invoke
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import tornadofx.View
import tornadofx.addClass
import tornadofx.importStylesheet
import tornadofx.tabpane
import tornadofx.tab
import tornadofx.vbox

class MainView : View("Hello TornadoFX") {

    val weights = NeuronNet(16, 2, 4).weights
    private val matricesViews = ArrayList<Matrix2DView<Double>>()

    override val root = ScrollPane()

    init {
        CSSUtils.addCss(root, "css/scroll-bar.css")
        importStylesheet(Styles::class)
        with(root) {
            addClass(Styles.main)
            tabpane {
                tab("Neuronet") {
                    add(NeuronetStructureView(weights))
                }

                tab("Weights") {
                    vbox {
                        (0 until weights.size) {
                            add(separator())
                            add(matrix(i))
                        }
                    }
                }
            }
        }

        tornadofx.runAsync {
            (1..10) {
                Thread.sleep(2000)
            }
            matricesViews[0]
        } ui {

        }

    }

    private fun matrix(i: Int): Matrix2DView<Double> {
        val matrix2D = weights[i]
        val uiComponent = Matrix2DView(i, matrix2D)
        matricesViews.add(uiComponent)
        return uiComponent
    }

    private fun separator(): Label {
        val label = Label()
        label.addClass(Styles.separator)
        return label
    }
}