package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.latest.MatrixType.*
import com.home.utils.functions.min
import com.home.utils.functions.only

enum class MatrixType(val isCorrect: (MatrixND<*>)-> Boolean) {
    SCALAR({matrix -> matrix.isScalar()}),
    VECTOR({matrix -> matrix.isVector()}),
    MATRIX2D({matrix -> matrix.has2Dimensions()}),
    MATRIX3D({matrix -> matrix.has3Dimensions()})
}

fun <T> MatrixND<T>.scalar(): T {
    Thrower.throwIf(this).isNot(SCALAR)
    return this.elementsContainer[0]
}

fun <T> MatrixND<T>.vector(): Vector<T> {
    Thrower.throwIf(this).isNot(VECTOR)
    return Vector(this.elementsContainer)
}

fun <T> MatrixND<T>.toMatrix2D(): Matrix2D<T> {
    Thrower.throwIf(this).isNot(MATRIX2D)
    return Matrix2D(this)
}

fun <T> MatrixND<T>.toMatrix3d(): Matrix3D<T> {
    Thrower.throwIf(this).isNot(MATRIX3D)
    return Matrix3D(this)
}

fun <T> MatrixND<T>.isScalar() = sizes.all { it == 1 }
fun <T> MatrixND<T>.isVector() = sizes.isNotEmpty() && sizes.only(1) { it > 1 }

fun <T> MatrixND<T>.isNotScalar() = !isScalar()
fun <T> MatrixND<T>.isNotVector() = !isVector()

fun <T> MatrixND<T>.hasDimension(dimension: Int): Boolean = isNotScalar() && isNotVector() && sizes.size == dimension && sizes.min(2) { it > 1 }
fun <T> MatrixND<T>.has2Dimensions() = this.hasDimension(2)
fun <T> MatrixND<T>.has3Dimensions() = this.hasDimension(3)
