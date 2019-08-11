package com.home.utils.elements

import com.home.utils.operators.get
import com.home.utils.operators.row
import com.home.utils.operators.set

open class Matrix2D<T> : Typed<T> {

    val rows: Int
    val cols: Int
    val elements: ArrayList<Vector<T>>

    constructor(rows: Int, cols: Int, type: Type<T>) : super(type) {
        this.rows = rows
        this.cols = cols
        this.elements = ArrayList()
        init()
    }

    constructor(size: Int, type: Type<T>) : super(type) {
        this.rows = size
        this.cols = this.rows
        this.elements = ArrayList()
        init()
    }

    private fun init() {
        (0 until this.rows).forEach { i ->
            val vector = super.type.vector(this.cols)
            (0 until this.cols).forEach { j ->
                vector[j] = super.type.init()
            }
            this.elements.add(i, vector)
        }
    }

    fun transpose(): Matrix2D<T> {
        val n = this.rows
        val m = this.cols
        val C = super.type.matrix(m, n)

        for (i in 0 until n)
            for (j in 0 until m)
                C[j][i] = this[i][j]
        return C
    }

    companion object {
        @JvmStatic
        fun<T> fill(creator: ()-> T, matrix: Matrix2D<T>) {
            val rows = matrix.rows
            val cols = matrix.cols
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    matrix[i][j] = creator()
                }
            }
        }
    }

}





