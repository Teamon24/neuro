package com.home.utils.elements.latest

import com.home.utils.elements.type.DoubleType
import com.home.utils.elements.type.Type

open class Matrix2D<T>(type: Type<T>, val rows: Int, val cols: Int) : MatrixNdim<T>(type, rows, cols) {

    fun col(index: Int): Vector<T> {
        throw UnsupportedOperationException("Matrix2D#get")
    }

    fun row(index: Int): Vector<T> {
        throw UnsupportedOperationException("Matrix2D#get")
    }
}
open class Matrix3D<T>(type: Type<T>, n: Int, m: Int, l: Int) : MatrixNdim<T>(type, n, m, l)

open class DoubleMatrix2D(n: Int, m: Int) : Matrix2D<Double>(DoubleType, n, m)
open class DoubleMatrix3D(n: Int, m: Int, l: Int) : Matrix3D<Double>(DoubleType, n, m, l)
