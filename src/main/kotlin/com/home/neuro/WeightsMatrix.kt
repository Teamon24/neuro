package com.home.neuro

import com.home.utils.elements.DoubleMatrix2D

class WeightsMatrix(private val weights: MutableList<DoubleMatrix2D>) {
    operator fun get(index: Int) = weights[index]
}