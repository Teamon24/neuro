package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed

open class Matrix3D<T> : Typed<T>
{
    val rows: Int
    val cols: Int
    val depths: Int

    private val matrix: Matrix<T>

    constructor(type: Type<T>,
                rows: Int,
                cols: Int,
                depths: Int) : super(type)
    {
        this.rows = rows
        this.cols = cols
        this.depths = depths
        this.matrix = Matrix(type, this.rows, this.cols, this.depths)
    }

    constructor(matrix: Matrix<T>) : super(matrix.type)
    {
        Thrower.throwIfWrongSize(3, matrix)
        this.rows = matrix.sizes[0]
        this.cols = matrix.sizes[1]
        this.depths = matrix.sizes[2]
        this.matrix = Matrix(type, this.rows, this.cols, this.depths)
    }

    operator fun get(index: Int): Matrix2D<T> {
        return this.matrix.cash(index).matrix2d()
    }

    operator fun set(value: Matrix2D<T>, index: Int) {
        val matrix = this.matrix[index].matrix2d()
        for (i in 0 until matrix.rows) {
            for (j in 0 until matrix.cols) {
                matrix[i][j] = value[i][j]
            }
        }
    }
}
