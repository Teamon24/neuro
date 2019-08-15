package com.home.utils.elements.latest

import com.home.utils.elements.type.Type
import java.lang.RuntimeException
import kotlin.reflect.KProperty0

class Vector<T>(type: Type<T>, size: Int) : MatrixNdim<T>(type, 1, size) {

    operator fun set(index: Int, value: T) {
        this.throwIfNegative(index)
        this.throwIfOverBound(index)
        this.container[index] = value
    }

    operator fun get(index: Int): T {
        this.throwIfNegative(index)
        this.throwIfOverBound(index)
        return this.container[index]
    }

    operator fun Vector<T>.plus(vector: Vector<T>): Vector<T> {
        checkSizes(this, vector)
        return reduce(type::plus, this, vector)
    }

    operator fun Vector<T>.minus(vector: Vector<T>): Vector<T> {
        checkSizes(this, vector)
        return reduce(type::minus, this, vector)
    }

    operator fun Vector<T>.times(vector: Vector<T>): T {
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

    fun <T> checkSizes(v1: Vector<T>, v2: Vector<T>) {
        if (v1.size1D != v2.size1D) {
            throw RuntimeException("Vectors sizes are not equal!")
        }
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
}