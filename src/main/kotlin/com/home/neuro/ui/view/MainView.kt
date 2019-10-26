package com.home.neuro.ui.view

import com.home.bot.ConsumerLineChart
import com.home.bot.Point
import com.home.neuro.NeuronNet
import com.home.neuro.ui.styles.Styles
import com.home.neuro.ui.styles.TabStyles
import com.home.utils.ImageUtils
import com.home.utils.Timer
import com.home.utils.css.CSSUtils
import io.reactivex.subjects.PublishSubject
import javafx.application.Platform
import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.Label
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import tornadofx.*
import java.awt.image.BufferedImage
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread


class MainView(
    val neuronNet: NeuronNet,
    val dataSet: Map<Int, List<BufferedImage>>
) : View("Hello TornadoFX") {

    private val matricesViews = ArrayList<Matrix2DView<Double>>()
    override val root = TabPane()

    init {
        importStylesheet(Styles::class)
        CSSUtils.addCss(root, "css/tab.css")
        Styles.tabAmount = 3

        with(root) {
            addClass(Styles.main)
            neuronetTab()
            errors(dataSet.keys, neuronNet)
            dataSetTab()

        }
    }

    private fun TabPane.errors(digits: Set<Int>,
                               neuronNet: NeuronNet) {
        tab("Errors Chart") {
            flowpane {
                for (digit in digits) {
                    val pointsSource = PublishSubject.create<Point<Int, Double>>()
                    val consumer = chart("digit: $digit")
                    pointsSource.subscribe(consumer)
                    val errorChart = consumer.lineChart
                    errorChart.addClass(Styles.errorChart)
                    this.add(errorChart)
                }
            }
        }
    }

    private fun chart(name: String) =
        object : ConsumerLineChart<Int, Double>(name, 50, Int::minus, { i1, i2 -> i1 > i2 }) {}

    private fun TabPane.weightsTab() {
        tab("Weights") {
            val size = neuronNet.weights.size
            tabpane {
                for (i in 0 until size) {
                    runAsync {
                        thread(start = true) {
                            Timer.count("Matrices View Creation") { this@MainView.matrixView(i) }
                        }
                    } ui {
                        TabStyles.tabsAmaount = size
                        addClass(TabStyles.matrixTabPane)
                        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                        Timer.count("Matrix Tab Initialization") {
                            matrixTab("${i + 1}\\${i + 2}", i)
                        }
                    }
                }
            }
        }
    }

    private fun TabPane.matrixTab(text: String, matrixTabNumber: Int) {
        tab(text) {
            val created = AtomicReference(false)
            whenSelected {
                Platform.runLater {
                    if (!created.get()) {
                        Timer.count("Adding Matrix View") { this@tab.add(matricesViews[matrixTabNumber]) }
                        created.set(true)
                    }
                }
            }
        }
    }

    private fun TabPane.neuronetTab(): Tab {
        return tab("Neuronet") {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            add(NeuronetStructureView(neuronNet.weights))
        }
    }

    private fun TabPane.dataSetTab(): Tab {
        return tab("DataSet") {
            tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
            vbox {
                dataSet.entries.forEach { (key, value) ->
                    hbox {
                        add(dataSetLabel(key))
                        value.forEach {
                            val resized = resize(it, dataSetUIImageSize)
                            add(ImageView(resized))
                        }
                    }
                }
            }
        }
    }

    private fun dataSetLabel(key: Int): Label {
        val child = Label("$key")
        child.addClass(Styles.dataSetGroup)
        return child
    }

    private fun resize(it: BufferedImage, newSize: Int): WritableImage {
        val toFXImage = SwingFXUtils.toFXImage(ImageUtils.resize(it, newSize, newSize), null)
        return toFXImage
    }

    private fun matrixView(i: Int): Matrix2DView<Double> {
        val matrix2D = neuronNet.weights[i]
        val uiComponent = Matrix2DView(i, matrix2D)
        matricesViews.add(uiComponent)
        return uiComponent
    }
}