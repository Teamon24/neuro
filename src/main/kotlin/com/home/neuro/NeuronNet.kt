package com.home.neuro

import com.home.utils.elements.latest.Vector
import com.home.utils.functions.Sum
import java.lang.RuntimeException
import kotlin.math.exp
import kotlin.math.round

class NeuronNet(
    private val inNeuronsAmount: Int,
    private val middleLayersAmount: Int,
    private val outNeuronsAmount: Int
) {
    private val neuronsPerMiddles = arrayListOf<Int>()
    private val neuronsPerLayers = arrayListOf<Int>()
    val weights: Weights
    val errors: Errors
    val errorsLayers: LayerErrors
    val reactions: Reactions
    private val getStep = { layers: Int -> round(layers.toDouble() / middleLayersAmount).toInt() }
    private val func = { signal: Double -> 1/ (1 + exp(-signal)) }



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
        weights = Weights(neuronsPerLayers)
        errors = Errors(neuronsPerLayers)
        errorsLayers = LayerErrors(neuronsPerLayers)
        reactions = Reactions(neuronsPerLayers)
    }

    fun react(stimulation: Vector<Double>) {
        val layersAmount = neuronsPerLayers.size
        for (k in 0 until layersAmount) {
            for (j in 1 until neuronsPerLayers[k]) {
                var prevLayerSize = neuronsPerLayers[k - 1]
                reactions[k][j] = func((0 until prevLayerSize).Sum({ i -> reactions[k][i] * weights[k][i][j] }, Double::plus))
            }
        }
    }

    fun correct(ethalon: Vector<Double>) {
        val lastLayerIndex = neuronsPerLayers.size - 1
        val lastErrorsLayer = errorsLayers[lastLayerIndex]
        for(j in 0 until lastErrorsLayer.size) {
            lastErrorsLayer[j] = ethalon[j] - reactions[lastLayerIndex][j]
        }
        throw UnsupportedOperationException("NeuronNet#correct is not implemented")
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
}
