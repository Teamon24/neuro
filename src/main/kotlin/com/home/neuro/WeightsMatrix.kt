package com.home.neuro

import com.home.utils.elements.Matrix2D

class WeightsMatrix<T>(val weights: MutableList<out Matrix2D<T>>) {
    operator fun get(index: Int) = weights[index]
}