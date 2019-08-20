package com.home.neuro

import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.type.Doubles
import com.home.utils.functions.i
import com.home.utils.functions.invoke

class Errors(layersSizes: ArrayList<Int>) : ArrayList<Matrix2D<Double>>() {
    init {
        (1 until layersSizes.size) {
            super.add(Doubles.matrix(layersSizes[i-1], layersSizes[i]))
        }
    }
}