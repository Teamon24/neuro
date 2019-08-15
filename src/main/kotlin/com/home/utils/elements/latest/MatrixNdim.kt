package com.home.utils.elements.latest

import com.google.common.collect.Lists
import com.home.utils.*
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.operators.allIndexesCombos
import com.home.utils.operators.plus
import kotlin.collections.ArrayList

/**
 * 1D-array-based matrix.
 */
open class MatrixNdim<T>(type: Type<T>, vararg val sizes: Int) : Typed<T>(type) {

    val size1D = P(this.sizes)
    val container = ArrayList<T>(this.size1D)
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

    fun get(vararg indexes: Int): T {
        this.throwIfAnyNegative(indexes)
        this.throwIfWrongDimension(indexes)
        val index = index1D(indexes)
        return this.container[index]
    }

    //TODO ОБЯЗАТЕЛЬНО НАПИСАТЬ ТЕСТ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    operator fun get(index: Int): MatrixNdim<T> {
        this.throwIfOverBound(index, this.sizes[0] - 1)
        val newSizes = this.sizes.deleteAt(0)
        val newMatrix = MatrixNdim(this.type, *newSizes)
        val expandedNewSizes = newSizes.map { (0..it).toList() }
        var cartesianProduct = Lists.cartesianProduct(expandedNewSizes)
        for (indexes in cartesianProduct) {
            val right = (index + indexes).toIntArray()
            val inversed = indexes.inverse().toIntArray()
            val value = this.get(*right)
            newMatrix.set(value, *inversed)
        }
        return newMatrix
    }

    fun isScalar() = size1D == 1
    fun isVector() = sizes.size >= 2 && sizes.only(1) { it > 1 }
    fun isMatrix() = sizes.size > 2 && sizes.min(2) { it > 1 }


    fun transpose(): MatrixNdim<T> {
        val transposed = MatrixNdim(super.type, *this.sizes);
        val cartesianProduct = allIndexesCombos()
        for (indexes in cartesianProduct) {
            val right = indexes.toIntArray()
            val inversed = indexes.inverse().toIntArray()
            val value = this.get(*right)
            transposed.set(value, *inversed)
        }
        return transposed
    }

    fun throwIfOverBound(index: Int) {
        this.throwIfOverBound(index, size1D)
    }

    fun throwIfOverBound(index: Int, bound: Int) {
        if (index >= bound) throwRex("Index is out of bound '$index'. Limit size: $bound")
    }

    fun throwIfNegative(index: Int) {
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


