package com.home.utils.elements

/**
 *
 */
class DoubleMatrix2D : Matrix2D<Double> {

    constructor(rows: Int, cols: Int) :
            super(
                rows,
                cols,
                Type<Double>(
                    { 0.0 },
                    Double::plus,
                    Double::minus,
                    Double::times
                )
            )

    constructor(size: Int) :
            super(
                size,
                Type<Double>(
                    { 0.0 },
                    Double::plus,
                    Double::minus,
                    Double::times
                )
            )

    override fun toString() = this.elements.toString()
}