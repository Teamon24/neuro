package com.home.utils.operators

import com.google.common.collect.Lists
import com.home.utils.elements.latest.MatrixND
import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.latest.Vector

operator fun<T> Matrix2D<T>.minus(B: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val C = this.type.matrix(n, m)
    for (i in 0 until n)
        for (j in 0 until m) {

            val resultVector: Vector<T> = C.row(i)
            val thisVector: Vector<T> = this[i]
            val bVector = B.row(i)

            resultVector[j] = thisVector[j] - bVector[j]
        }
    return C
}

operator fun<T> Matrix2D<T>.plus(B: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val C = this.type.matrix(n, m)
    for (i in 0 until n) {
        for (j in 0 until m) {
            val resultVector: Vector<T> = C.row(i)
            val thisVector: Vector<T> = this.row(i)
            val bVector = B.row(i)
            resultVector[j] = thisVector[j] + bVector[j]
        }
    }
    return C
}

operator fun<T> Matrix2D<T>.times(matrix: Matrix2D<T>): Matrix2D<T> {
    val n = this.rows
    val m = this.cols
    val result = this.type.matrix(n, m)
    for (i in 0 until n) {
        for (j in 0 until m) {
            result[i][j] = this[i] * matrix.col(j)
        }
    }
    return result
}

fun <T> MatrixND<T>.allIndexesCombos(): List<List<Int>> = Lists.cartesianProduct(sizes.map { (0 until it).toList() })
fun allRangeCombos(sizes: IntArray): List<List<Int>> = Lists.cartesianProduct(sizes.map { (1..it).toList() })