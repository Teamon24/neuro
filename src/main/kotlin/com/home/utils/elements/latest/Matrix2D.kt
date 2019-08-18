package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.type.Doubles
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.functions.i
import com.home.utils.functions.invoke

class Matrix2D<T> : Typed<T> {
    val rows: Int
    val cols: Int
    private val matrix: Matrix<T>

    constructor(type: Type<T>, rows: Int, cols: Int) : super(type) {
        this.rows = rows
        this.cols = cols
        this.matrix = Matrix(type, this.rows, this.cols)
    }

    constructor(container: Container<T>, vararg sizes: Int) : super(container.type) {
        this.rows = sizes[0]
        this.cols = sizes[1]
        this.matrix = Matrix(type, container, *sizes)
    }

    constructor(matrix: Matrix<T>) : super(matrix.type) {
        Thrower.throwIfWrongSize(2, matrix)
        this.rows = matrix.container.sizes[0]
        this.cols = matrix.container.sizes[1]
        this.matrix = matrix
    }

    operator fun get(index: Int): Vector<T> {
        return this.matrix.cash(index).vector()
    }

    operator fun set(value: Vector<T>, index: Int) {
        val gotten = this.matrix[index].vector()
        if (value.size == gotten.size) {
            (0 until gotten.size) {
                gotten[i] = value[i]
            }
        } else {
            Thrower.throwObjectIsNot(Vector::class, index)
        }
    }

    fun col(index: Int): Vector<T> {
        throw UnsupportedOperationException("Matrix2D#col")
    }

    fun row(index: Int): Vector<T> {
        throw UnsupportedOperationException("Matrix2D#row")
    }
}
