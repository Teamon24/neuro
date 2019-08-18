package com.home.neuro

import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.type.Doubles
import com.home.utils.functions.i
import com.home.utils.functions.invoke


class Weights(layers: ArrayList<Int>) : ArrayList<Matrix2D<Double>>() {
    init {
        (1 until layers.size) {
            super.add(Doubles.matrix(layers[i-1], layers[i]))
        }
    }
}