package com.home.utils.operators

import com.home.utils.elements.Vector
import java.lang.RuntimeException
import kotlin.reflect.KProperty0

/**
 * Установка значения в вектор.
 */
operator fun <T> Vector<T>.set(index: Int, value: T) { this.elements[index] = value }

/**
 * Получение значения вектора.
 */
operator fun <T> Vector<T>.get(index: Int): T = this.elements[index]

/**
 * Прибавление элемента к вектору.
 */
operator fun <T> Vector<T>.plus(element: T) = this.forEach { it + element}

/**
 * Вычитаение элемента от вектора.
 */
operator fun <T> Vector<T>.minus(element: T) = this.forEach { it - element }




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
    for(i in 0 until this.size) {
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

fun <T> checkSizes(v1: Vector<T>, v2: Vector<T>) {
    if (v1.size != v2.size) {
        throw RuntimeException("Vectors sizes are not equal!")
    }
}

private fun <T> reduce(reducer: KProperty0<(T, T) -> T>, v1: Vector<T>, v2: Vector<T>): Vector<T>
{
    val size = v1.size
    val result: Vector<T> = v1.type.vector(size)

    for (i in 0 until size) {
        val operation = reducer.get()
        result[i] = operation(v1[i], v2[i])
    }

    return result
}

