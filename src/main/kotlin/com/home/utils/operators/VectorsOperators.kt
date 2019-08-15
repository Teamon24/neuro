package com.home.utils.operators

import com.home.utils.elements.latest.Vector
import java.lang.RuntimeException
import kotlin.reflect.KProperty0

/**
 * Сложение векторов.
 */
operator fun <T> Vector<T>.plus(vector: Vector<T>): Vector<T> {
    checkSizes(this, vector)
    return reduce(type::plus, this, vector)
}

/**
 * Вычитание векторов.
 */
operator fun <T> Vector<T>.minus(vector: Vector<T>): Vector<T> {
    checkSizes(this, vector)
    return reduce(type::minus, this, vector)
}

/**
 * Умножение векторов.
 */
operator fun <T> Vector<T>.times(vector: Vector<T>): T {
    checkSizes(this, vector)
    var result: T? = null
    for(i in 0 until this.size1D) {
        val thisElement: T = this[i]
        val vectorElement: T = vector[i]
        val times = thisElement * vectorElement
        if (result == null) {
            result = times
        } else {
            result += times
        }
    }

    return result!!
}

private fun <T> reduce(reducer: KProperty0<(T, T) -> T>, v1: Vector<T>, v2: Vector<T>): Vector<T>
{
    val size = v1.size1D
    val result: Vector<T> = v1.type.vector(size)

    for (i in 0 until size) {
        val operation = reducer.get()
        result[i] = operation(v1[i], v2[i])
    }

    return result
}

