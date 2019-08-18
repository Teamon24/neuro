package com.home.utils.elements.type

import com.home.utils.elements.latest.Container
import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.latest.Vector
import kotlin.reflect.KClass

/**
 *
 */
abstract class Type<T> (
    val init:  (    ) -> T,
    val plus:  (T, T) -> T,
    val minus: (T, T) -> T,
    val times: (T, T) -> T
) {
    fun matrix(newContainer: Container<T>, newSizes: IntArray) = Matrix(this, newContainer, *newSizes)
    fun matrix(vararg sizes: Int, container: Container<T>) = Matrix(this, container, *sizes)
    fun matrix(vararg sizes: Int) = Matrix(this, *sizes)

    fun matrix(rows: Int, columns: Int) = Matrix2D(this, rows, columns)
    fun matrix(size: Int) = Matrix2D(this, size, size)
    fun vector(size: Int) = Vector(this, size)
    abstract fun clazz(): KClass<out Any>

}
