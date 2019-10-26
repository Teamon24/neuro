package com.home.neuro

import com.home.utils.elements.latest.Vector
import com.home.utils.functions.Sum
import java.lang.RuntimeException
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.exp
import kotlin.math.round
import kotlin.random.Random

class NeuronNet {
    private val inNeuronsAmount: Int
    private val middleLayersAmount: Int
    private val outNeuronsAmount: Int

    constructor(inNeuronsAmount: Int, middleLayersAmount: Int, outNeuronsAmount: Int) {
        this.inNeuronsAmount = inNeuronsAmount
        this.middleLayersAmount = middleLayersAmount
        this.outNeuronsAmount = outNeuronsAmount
        this.neuronsPerMiddles = arrayListOf()
        this.neuronsPerLayers = arrayListOf()
        this.getStep = { layers: Int -> round(layers.toDouble() / middleLayersAmount).toInt() }
        this.sigmoida = { signal: Double -> 1 / (1 + exp(-signal)) }
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
        this.round = { mode, scale ->
            BigDecimal(this).setScale(scale, mode).toDouble()
        }
        this.init()
    }

    private val neuronsPerMiddles: ArrayList<Int>
    private val neuronsPerLayers: ArrayList<Int>
    val weights: Weights
    val errors: Errors
    val errorsLayers: LayerErrors
    val reactions: Reactions
    private val getStep: (Int) -> Int
    private val sigmoida: (Double) -> Double



    fun react(stimulation: Vector<Double>) {
        if (stimulation.size != this.inNeuronsAmount) throw RuntimeException("stimulation and input layer has different size.")
        val layersAmount = neuronsPerLayers.size
        for (k in 0 until layersAmount) {
            for (j in 1 until neuronsPerLayers[k]) {
                val prevLayerSize = neuronsPerLayers[k - 1]
                val signal = (0 until prevLayerSize).Sum({ i -> reactions[k][i] * weights[k][i][j] }, Double::plus)
                reactions[k][j] = sigmoida(signal)
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

    val random = Random(0)
    fun init() {
        for (weight in weights) {
            for (i in 0 until weight.rows) {
                val vector = weight[i]
                for (j in 0 until vector.size) {
                    vector[j] = Random(random.nextInt()).nextDouble().round(RoundingMode.HALF_EVEN, 4)
                }
            }
        }
    }


    val round: Double.(RoundingMode, Int) -> Double

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
