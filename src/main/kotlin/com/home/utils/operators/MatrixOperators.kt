package com.home.utils.operators

import com.home.utils.elements.Column
import com.home.utils.elements.Matrix2D
import com.home.utils.elements.Vector


fun<T> Matrix2D<T>.row(index: Int): Vector<T> = type.row(this.elements[index])

fun<T> Matrix2D<T>.col(col: Int): Column<T> {
    val column = type.column(this.rows)
    for (i in 0 until this.rows) {
        column[i] = this[i][col]
    }
    return column
}

operator fun<T> Matrix2D<T>.get(index: Int): Vector<T> = this.elements[index]
operator fun<T> Matrix2D<T>.set(index: Int, value: Vector<T>) {this.elements[index] = value}

operator fun<T> Matrix2D<T>.minus(B: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val C = this.type.matrix(n, m)
    for (i in 0 until n)
        for (j in 0 until m) {

            val resultVector: Vector<T> = C[i]
            val thisVector: Vector<T> = this[i]
            val bVector = B[i]

            resultVector[j] = thisVector[j] - bVector[j]
        }
    return C
}

operator fun<T> Matrix2D<T>.plus(B: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val C = this.type.matrix(n, m)
    for (i in 0 until n)
        for (j in 0 until m)
            C[i][j] = this[i][j] + B[i][j]
    return C
}

operator fun<T> Matrix2D<T>.times(matrix: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val result = this.type.matrix(n, m)
    for (i in 0 until n)
        for (j in 0 until m) {
            result[i][j] = this[i] * matrix[m]
        }
    return result
}