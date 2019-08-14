package com.home.utils.elements.latest

import com.home.utils.P
import com.home.utils.S
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.operators.plus

/**
 * 1D-array-based matrix.
 */
open class Matrix<T>(type: Type<T>, vararg val sizes: Int) : Typed<T>(type) {

    private val size1D = P(this.sizes)
    private val container = ArrayList<T>(this.size1D)
    private val D = this.sizes.size
    private val N = 1 + sizes
    private val index1D = { e: IntArray -> (0 until D).S { i -> e[i] * (0..i).P(N)}}

    init {
        repeat(size1D) { this.container.add(super.type.init()) }
    }

    fun set(value: T, vararg indexes: Int) {
        this.throwIfAnyNegative(indexes)
        this.throwIfWrongDimension(indexes)
        val index = index1D(indexes)
        this.container[index] = value
    }

    fun set(value: T, index: Int) {
        this.throwIfNegative(index)
        this.throwIfOverBound(index)
        this.container[index] = value
    }

    fun get(vararg indexes: Int): T {
        this.throwIfAnyNegative(indexes)
        this.throwIfWrongDimension(indexes)
        val index = index1D(indexes)
        return this.container[index]
    }

    fun add(value: T) {
        this.throwIfOverBound(container.size)
        this.container.add(value)
    }

    fun get(index: Int): T {
        this.throwIfNegative(index)
        this.throwIfOverBound(index)
        return this.container[index]
    }

    fun isScalar() = size1D == 1
    fun isVector() = sizes.size == 2 && sizes.only(1) { it > 1 }
    fun isMatrix() = sizes.size > 2 && sizes.min(2) { it > 1 }

    private fun throwIfOverBound(index: Int) {
        if (index >= size1D) throwRex("Index is out of bound '$index'. Limit size for 1D-array: $size1D")
    }

    private fun throwIfNegative(index: Int) {
        if (index < 0) throwRex("Index is negative: '$index'.")
    }

    private fun throwIfWrongDimension(indexes: IntArray) {
        val size = indexes.size
        if (size != D) throwRex("Indexes amount '$size' is not equal to matrix dimension: '$D'.")
    }

    private fun throwIfAnyNegative(indexes: IntArray) {
        if (indexes.find { it < 0 } != null) {
            val negativeIndexes = indexes.withIndex().filter { it.value < 0 }
            val negatives = negativeIndexes.joinToString(truncated = "", transform = { "'${it.index}': ${it.value}" })
            throwRex("Next indexes are negatives: $negatives.")
        }
    }

    private fun throwRex(message: String): Nothing = throw RuntimeException(message)
}

private fun IntArray.only(amount: Int, predicate: (Int) -> Boolean) = this.filter { predicate(it) }.count() == amount
private fun IntArray.min(amount: Int, predicate: (Int) -> Boolean) = this.filter { predicate(it) }.count() >= amount
private fun IntArray.max(amount: Int, predicate: (Int) -> Boolean) = this.filter { predicate(it) }.count() <= amount

private fun IntArray.any(predicate: (Int) -> Boolean) = min(1, predicate)
private fun IntArray.all(predicate: (Int) -> Boolean) = min(this.size, predicate)



