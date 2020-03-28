package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.functions.prod
import com.home.utils.functions.downOn
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed
import com.home.utils.functions.deleteAt

class ElementsContainer<T>(
    type: Type<T>,
    val sizes: IntArray,
    private val hardIndexes: HashMap<Int, Int> = HashMap(),
    elements: ArrayList<T> = ArrayList()
) : Typed<T>(type) {

    var start: Int = 0
    var end: Int = TO_1D_INDEX(this.sizes, this.sizes downOn 1)
    var elements: ArrayList<T> = ArrayList()
    var hardIndexesCounter = -1;

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

    fun desizeFirst(sameIndexNumber: Boolean, index: Int): ElementsContainer<T> {
        if (!sameIndexNumber) {
            hardIndexesCounter++
        }
        this.hardIndexes[hardIndexesCounter] = index
        var d = this.hardIndexes.size
        val newSizes = this.sizes.dropWhile { --d; d >= 0 }.toIntArray()
        val p = prod(newSizes)
        val startIndex = this.start + index * p
        val i = this.hardIndexes.size - 1
        val i1 = this.sizes[i] - 1 - index
        val lastIndex = this.end - i1 * p
        val ceasedContainer = ElementsContainer(this.type, this.sizes.deleteAt(0), this.hardIndexes, this.elements)
        ceasedContainer.start = startIndex
        ceasedContainer.end = lastIndex
        return ceasedContainer
    }
}
