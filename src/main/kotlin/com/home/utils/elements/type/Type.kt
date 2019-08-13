package com.home.utils.elements.type

import com.home.utils.elements.Column
import com.home.utils.elements.Matrix2D
import com.home.utils.elements.Row
import com.home.utils.elements.Vector

/**
 *
 */
abstract class Type<T> (
    val init:  (    ) -> T,
    val plus:  (T, T) -> T,
    val minus: (T, T) -> T,
    val times: (T, T) -> T
) {
    fun matrix(rows: Int, columns: Int) = Matrix2D(rows, columns, this)
    fun matrix(size: Int) = Matrix2D(size, this)
    fun vector(size: Int) = Vector(size, this)
    fun column(size: Int) = Column(size, this)
    fun row(size: Int) = Row(size, this)
    fun row(vector: Vector<T>) = Row(vector, this)
}