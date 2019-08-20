package com.home.neuro

import com.home.utils.elements.latest.Vector
import com.home.utils.elements.type.Doubles
import com.home.utils.functions.i
import com.home.utils.functions.invoke

/**
 *
 */
class Reactions (layersSizes: ArrayList<Int>) : ArrayList<Vector<Double>>(layersSizes.size) {
    init {
        (0 until layersSizes.size) {
            super.add(Doubles.vector(layersSizes[i]))
        }
    }
}