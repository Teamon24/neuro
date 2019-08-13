package com.home.utils.elements

import com.home.utils.elements.type.DoubleType

class DoubleMatrix2D : Matrix2D<Double> {
    constructor(rows: Int, cols: Int) : super(rows, cols, DoubleType)
    constructor(size: Int) : super(size, DoubleType)
    override fun toString() = this.elements.toString()
}