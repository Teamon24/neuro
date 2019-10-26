package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.latest.MatrixType.*
import com.home.utils.functions.min
import com.home.utils.functions.only

enum class MatrixType(val isCorrect: (Matrix<*>)-> Boolean) {
    SCALAR({matrix -> matrix.isScalar()}),
    VECTOR({matrix -> matrix.isVector()}),
    MATRIX2D({matrix -> matrix.isMatrix2D()}),
    MATRIX3D({matrix -> matrix.isMatrix3D()}),
    MATRIX({matrix -> matrix.isMatrix()})
}

fun <T> Matrix<T>.scalar(): T {
    Thrower.throwIf(this).isNot(SCALAR)
    return this.elementsContainer[0]
}

fun <T> Matrix<T>.vector(): Vector<T> {
    Thrower.throwIf(this).isNot(VECTOR)
    return Vector(this.elementsContainer)
}

fun <T> Matrix<T>.matrix2d(): Matrix2D<T> {
    Thrower.throwIf(this).isNot(MATRIX2D)
    return Matrix2D(this)
}

fun <T> Matrix<T>.matrix3d(): Matrix3D<T> {
    Thrower.throwIf(this).isNot(MATRIX3D)
    return Matrix3D(this)
}

fun <T> Matrix<T>.isScalar() = sizes.all { it == 1 }
fun <T> Matrix<T>.isVector() = sizes.isNotEmpty() && sizes.only(1) { it > 1 }

fun <T> Matrix<T>.isNotScalar() = !isScalar()
fun <T> Matrix<T>.isNotVector() = !isVector()

fun <T> Matrix<T>.isMatrix(dimension: Int): Boolean = isNotScalar() && isNotVector() && sizes.size == dimension && sizes.min(2) { it > 1 }
fun <T> Matrix<T>.isMatrix() = sizes.min(2) { it > 1 }
fun <T> Matrix<T>.isMatrix2D() = this.isMatrix(2)
fun <T> Matrix<T>.isMatrix3D() = this.isMatrix(3)
fun <T> Matrix<T>.isMatrix4D() = this.isMatrix(4)

fun <T> Matrix<T>.isNotMatrix2D() = !isMatrix2D()
