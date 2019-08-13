package com.home.neuro

import com.home.utils.elements.DoubleMatrix2D
import java.lang.RuntimeException
import kotlin.math.round

class Neurons(
    val inNeuronsAmount: Int,
    val middleLayersAmount: Int,
    val outNeuronsAmount: Int
) {
    val neuronsPerMiddles = arrayListOf<Int>()
    val neuronsPerLayers = arrayListOf<Int>()
    var weightsMatrix: WeightsMatrix<Double>
    private val getStep = { layers: Int -> round(layers.toDouble() / middleLayersAmount).toInt() }

    init {
        val possibleLayers = inNeuronsAmount - outNeuronsAmount - 1

        when {
            possibleLayers < 0 -> throw RuntimeException("Входящих нейронов меньше чем выходящих.")
            middleLayersAmount > possibleLayers -> throw RuntimeException("""Слоев столько, что не возможно создать слои с минимальной разницей в 1 нейрон между ними.""")
            middleLayersAmount == 1 -> neuronsPerMiddles.add(possibleLayers / 2)
            middleLayersAmount == possibleLayers -> (inNeuronsAmount - 1 downTo outNeuronsAmount + 1).forEach {
                neuronsPerMiddles.add(it)
            }
            else -> {
                val step = this.getStep(possibleLayers)
                this.populateNeuronsPerMiddles(step)
                this.repopulateNeuronsPerMiddles(step)
            }
        }

        neuronsPerLayers.add(inNeuronsAmount)
        neuronsPerLayers.addAll(neuronsPerMiddles)
        neuronsPerLayers.add(outNeuronsAmount)
        weightsMatrix = this.emptyWeightsMatrix()
    }

    private fun populateNeuronsPerMiddles(step: Int) {
        (1..middleLayersAmount).forEach {
            neuronsPerMiddles.add(inNeuronsAmount - step * it)
        }
    }

    private fun repopulateNeuronsPerMiddles(step: Int) {
        val lessOrSames = neuronsPerMiddles.filter { it <= outNeuronsAmount }.count()

        if (lessOrSames > 0) {
            val newSteps = arrayListOf(
                neuronsPerMiddles.size - 2 * lessOrSames to step,
                lessOrSames to step - 1,
                lessOrSames to 1
            )

            val newNeuronsPerLayers = arrayListOf<Int>()
            var next = inNeuronsAmount
            newSteps.forEach { (numbers, step) ->
                repeat(numbers) {
                    next -= step
                    newNeuronsPerLayers.add(next)
                }
            }
            this.neuronsPerMiddles.clear()
            this.neuronsPerMiddles.addAll(newNeuronsPerLayers)
        }
    }

    private fun emptyWeightsMatrix(): WeightsMatrix<Double> {
        val neuronsPerLayers = neuronsPerLayers
        val columns = neuronsPerLayers.size - 1

        val weights: MutableList<DoubleMatrix2D> = mutableListOf()
        weights.clear()
        (0 until columns).forEach { i ->
            val n = neuronsPerLayers[i]
            val m = neuronsPerLayers[i + 1]
            val element = DoubleMatrix2D(n, m)
            weights.add(i, element)
        }
        return WeightsMatrix(weights)
    }
}
