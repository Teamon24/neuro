package com.home.utils.elements

/**
 *
 */
class Type<T, R> (
    val init:  (    ) -> T,
    val plus:  (T, T) -> R,
    val minus: (T, T) -> R,
    val times: (T, T) -> R
) {
    fun matrix(rows: Int, columns: Int) = Matrix2D(rows, columns, this)
    fun matrix(size: Int) = Matrix2D(size, this)
    fun vector(size: Int) = Vector(size, this)
    fun column(size: Int) = Column(size, this)
    fun row(size: Int) = Row(size, this)
    fun row(vector: Vector<T>) = Row(vector, this)
}
