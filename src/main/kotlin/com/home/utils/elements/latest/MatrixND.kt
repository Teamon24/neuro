package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.functions.*
import com.home.utils.operators.allIndexesCombos

/**
 * 1D-array-based n-dimensional matrix.
 */
class MatrixND<T> : Typed<T>
{
    val sizes: IntArray
    private val size1D: Int
    val elementsContainer: ElementsContainer<T>
    private val cash = HashMap<Int, MatrixND<T>>()
    private var sameIndexNumber = false

    override fun toString(): String {
        return "Matrix(dimension = ${sizes.size}, sizes = {${sizes.joinToString(truncated = "")}})"
    }

    constructor(type: Type<T>,
                sizes: List<Int>) : super(type)
    {
        val intSizes = sizes.toIntArray()
        Thrower.throwIfEmpty(intSizes)
        this.sizes = intSizes
        this.size1D = prod(this.sizes)
        this.elementsContainer = ElementsContainer(type, this.sizes)
    }

    constructor(type: Type<T>,
                vararg sizes: Int) : super(type)
    {
        Thrower.throwIfEmpty(sizes)
        this.sizes = sizes
        this.size1D = prod(this.sizes)
        this.elementsContainer = ElementsContainer(type, this.sizes)
    }

    constructor(type: Type<T>,
                elementsContainer: ElementsContainer<T>,
                vararg sizes: Int): super(type)
    {
        Thrower.throwIfEmpty(sizes)
        this.sizes = sizes
        this.size1D = prod(this.sizes)
        this.elementsContainer = elementsContainer
    }

    fun set(value: T, vararg indexes: Int) {
        Thrower.throwIfAnyNegative(indexes)
        Thrower.throwIfWrongDimension(indexes, sizes)
        val index = TO_1D_INDEX(sizes, indexes)
        this.elementsContainer[index] = value
    }

    fun getAt(indexes: List<Int>): T {
        Thrower.throwIfAnyNegative(indexes)
        Thrower.throwIfWrongDimension(indexes, sizes)
        val index = TO_1D_INDEX(sizes, indexes)
        return this.elementsContainer[index]
    }

    fun getAt(vararg indexes: Int): T {
        Thrower.throwIfAnyNegative(indexes)
        Thrower.throwIfWrongDimension(indexes, sizes)
        val index = TO_1D_INDEX(sizes, indexes)
        return this.elementsContainer[index]
    }

    operator fun get(index: Int): MatrixND<T> {
        Thrower.throwIfOverBound(index, this.sizes[0] - 1)
        Thrower.throwIfBelowBound(index, this.sizes[0] - 1)
        Thrower.throwIfScalar(index, this.sizes.size)
        return this.cash(index)
    }

    internal fun cash(index: Int): MatrixND<T> {
        val matrix = this.cash[index]
        return if (matrix == null) {
            val newSizes = this.sizes.deleteAt(0)
            val newContainer = this.elementsContainer.desizeFirst(sameIndexNumber, index)
            sameIndexNumber = true
            val newMatrix = this.type.matrix(newContainer, newSizes)
            this.cash[index] = newMatrix
            newMatrix
        } else {
            matrix
        }
    }

    fun transpose(): MatrixND<T> {
        val transposed = MatrixND(super.type, *this.sizes)
        val cartesianProduct = allIndexesCombos()
        for (indexes in cartesianProduct) {
            val right = indexes.toIntArray()
            val inversed = indexes.inverse().toIntArray()
            val value = this.getAt(*right)
            transposed.set(value, *inversed)
        }
        return transposed
    }

    fun forEach(operation: IntRange.(ElementsContainer<T>) -> Unit) {
        val container = this@MatrixND.elementsContainer
        (container.start..container.end) {
            operation(this, container)
        }
    }

    fun setAt(indexes: List<Int>, value: T) {
        val index1D = TO_1D_INDEX(this.sizes, indexes)
        this.elementsContainer.set(index1D, value)
    }
}


