package com.home.neuro

import com.home.utils.elements.DoubleMatrix2D
import com.home.utils.operators.get
import java.lang.RuntimeException
import kotlin.math.round

class NeuroConfigs(
    val firstsAmount: Int,
    val middlesAmount: Int,
    val outputsAmount: Int
) {
    val neuronsPerMiddles = arrayListOf<Int>()
    val neuronsPerLayers = arrayListOf<Int>()
    private val getStep = { layers: Int -> round(layers.toDouble() / middlesAmount).toInt() }

    init {
        val possibleLayers = firstsAmount - outputsAmount - 1

        when {
            possibleLayers < 0 -> throw RuntimeException("Входящих нейронов меньше чем выходящих.")
            middlesAmount > possibleLayers -> throw RuntimeException("""Слоев столько, что не возможно создать слои с минимальной разницей в 1 нейрон между ними.""")
            middlesAmount == 1 -> neuronsPerMiddles.add(possibleLayers / 2)
            middlesAmount == possibleLayers -> (firstsAmount - 1 downTo outputsAmount + 1).forEach {
                neuronsPerMiddles.add(
                    it
                )
            }
            else -> {
                val step = this.getStep(possibleLayers)
                this.populateNeuronsPerMiddles(step)
                this.repopulateNeuronsPerMiddles(step)
            }
        }

        neuronsPerLayers.add(firstsAmount)
        neuronsPerLayers.addAll(neuronsPerMiddles)
        neuronsPerLayers.add(outputsAmount)

    }

    private fun populateNeuronsPerMiddles(step: Int) {
        (1..middlesAmount).forEach {
            neuronsPerMiddles.add(firstsAmount - step * it)
        }
    }

    private fun repopulateNeuronsPerMiddles(step: Int) {
        val lessOrSames = neuronsPerMiddles.filter { it <= outputsAmount }.count()

        if (lessOrSames > 0) {
            val newSteps = arrayListOf(
                neuronsPerMiddles.size - 2 * lessOrSames to step,
                lessOrSames to step - 1,
                lessOrSames to 1
            )

            val newNeuronsPerLayers = arrayListOf<Int>()
            var next = firstsAmount
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

fun main() {
    val configs = NeuroConfigs(16, 5, 3)
    val neuronsPerLayers = configs.neuronsPerMiddles
    println("""${configs.firstsAmount} $neuronsPerLayers ${configs.outputsAmount}""")
    val weights = emptyWeightsMatrix(configs)
    val i = 0 // 0 - i-ый слой весов.
    val (j, k) = 0 to 0 // индексы для j-го нейрон и k-го нейрон в i-ом слое весов.
    weights[i][j][k] // вес между j-ым во входном и первым в первом промежуточном слое.
}


fun emptyWeightsMatrix(configs: NeuroConfigs): WeightsMatrix {
    val neuronsPerLayers = configs.neuronsPerLayers
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



