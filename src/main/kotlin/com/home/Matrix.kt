package com.home

import com.home.utils.P
import com.home.utils.S
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.operators.*
import kotlin.collections.ArrayList

/**
 * N-dimensional matrix.
 */
class Matrix<T>(type: Type<T>, vararg sizes: Int) : Typed<T>(type) {

    private var size1D = P(sizes)
    private var container = ArrayList<T>(size1D)
    private var D = sizes.size
    private var N = 1 + sizes
    private val i = { e: IntArray ->
        (0 until D).S { i -> e[i] * (0..i).P(N)}
    }

    init {
        repeat(size1D) { this.container.add(super.type.init()) }
    }

    fun set(value: T, vararg indexes: Int) {
        this.throwIfLessOrMore(indexes)
        val index = i(indexes)
        this.container.add(index, value)
    }

    fun set(value: T, index: Int) {
        this.throwIfOverBound(index)
        this.container[index] = value
    }

    fun get(vararg indexes: Int): T {
        this.throwIfLessOrMore(indexes)
        val index = i(indexes)
        return this.container[index]
    }

    fun add(value: T) {
        this.throwIfOverBound(container.size)
        this.container.add(value)
    }

    private fun throwIfOverBound(index: Int) {
        if (index == size1D) throwRex("There is no free space")
    }

    private fun throwIfLessOrMore(indexes: IntArray) {
        if (indexes.size != D) throwRex("Indexes amount is not equal to matrix dimension.")
    }

    private fun throwRex(message: String): Nothing = throw RuntimeException(message)
}





