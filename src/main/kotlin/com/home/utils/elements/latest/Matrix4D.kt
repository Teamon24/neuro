package com.home.utils.elements.latest

import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed

/**
 *
 */
class Matrix4D<T>(
    type: Type<T>,
    val i: Int,
    val j: Int,
    val k: Int,
    val l: Int
) : Typed<T>(type)
{
    private val matrix = Matrix(type, this.i, this.j, this.k, this.l)

    operator fun get(index: Int): Matrix3D<T> {
        return this.matrix.cash(index).matrix3d()
    }

    operator fun set(value: Matrix3D<T>, index: Int) {
        val matrix = this.matrix[index].matrix3d()
        for (i in 0 until matrix.rows) {
            for (j in 0 until matrix.cols) {
                for (k in 0 until matrix.depths) {
                    matrix[i][j][k] = value[i][j][k]
                }
            }
        }
    }
}