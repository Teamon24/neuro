package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.functions.Prod
import com.home.utils.functions.downOn
import com.home.utils.elements.type.Integers
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed

class Container<T>(
    type: Type<T>,
    val sizes: IntArray,
    private val hardIndexes: ArrayList<Int> = ArrayList(),
    elements: ArrayList<T> = ArrayList()
) : Typed<T>(type) {
    var start: Int = 0
    var end: Int = index1D(this.sizes, this.sizes downOn 1)
    var elements: ArrayList<T> = ArrayList()

    init {
        if (elements.isEmpty() && this.elements.isEmpty()) {
            repeat(end + 1) { this.elements.add(super.type.init()) }
        } else {
            this.elements = elements
        }
    }

    fun add(value: T)  = this.elements.add(value)

    operator fun set(index: Int, value: T) {
        val local = index
        Thrower.throwIfOverBound(local, this.end)
        val global = this.start + index
        Thrower.throwIfBelowBound(this.start, global)
        this.elements[global] = value
    }

    operator fun get(index: Int): T {
        val local = index
        Thrower.throwIfOverBound(local, this.end)
        val global = this.start + index
        Thrower.throwIfBelowBound(this.start, global)
        return this.elements[global]
    }

    fun desizeFirst(index: Int): Container<T> {
        this.hardIndexes.add(index)
        var d = this.hardIndexes.size
        val p = Prod(this.sizes.dropWhile { d--; d >= 0 }.toIntArray())
        val startIndex = this.start + index * p
        val lastIndex = this.end - (sizes[this.hardIndexes.size - 1] - 1 - index) * p
        val ceasedContainer = Container(type, this.sizes, this.hardIndexes, this.elements)
        ceasedContainer.start = startIndex
        ceasedContainer.end = lastIndex
        return ceasedContainer
    }
}



fun main() {
    val matrix2d = Matrix2D(Integers, 3, 3)
    val vector = matrix2d[1]
    val scalar = vector[1]

    println()
}
