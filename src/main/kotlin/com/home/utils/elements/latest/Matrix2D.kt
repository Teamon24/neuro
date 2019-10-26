package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.functions.i
import com.home.utils.functions.invoke

class Matrix2D<T> : Typed<T> {
    val rows: Int
    val cols: Int
    val base: Matrix<T>

    constructor(type: Type<T>, rows: Int, cols: Int) : super(type) {
        this.rows = rows
        this.cols = cols
        this.base = Matrix(type, this.rows, this.cols)
    }

    constructor(elementsContainer: ElementsContainer<T>, vararg sizes: Int) : super(elementsContainer.type) {
        this.rows = sizes[0]
        this.cols = sizes[1]
        this.base = Matrix(type, elementsContainer, *sizes)
    }

    constructor(matrix: Matrix<T>) : super(matrix.type) {
        Thrower.throwIfWrongSize(2, matrix)
        this.rows = matrix.elementsContainer.sizes[0]
        this.cols = matrix.elementsContainer.sizes[1]
        this.base = matrix
    }

    operator fun get(index: Int): Vector<T> {
        return this.base.cash(index).vector()
    }

    operator fun set(value: Vector<T>, index: Int) {
        val gotten = this.base[index].vector()
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
