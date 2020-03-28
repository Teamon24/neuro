package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed

class Vector<T> : Typed<T> {

    private val elementsContainer: ElementsContainer<T>
    val size: Int

    constructor(type: Type<T>, size: Int) : super(type) {
        this.elementsContainer = MatrixND(type, 1, size).elementsContainer
        this.size = size(this.elementsContainer)
    }

    constructor(elementsContainer: ElementsContainer<T>) : super(elementsContainer.type) {
        this.elementsContainer = elementsContainer
        this.size = size(this.elementsContainer)
    }

    operator fun set(index: Int, value: T) {
        Thrower.throwIfNegative(index)
        Thrower.throwIfOverBound(index, this.size - 1)
        this.elementsContainer[index] = value
    }


    operator fun get(index: Int): T {
        Thrower.throwIfNegative(index)
        Thrower.throwIfOverBound(index, this.size - 1)
        return this.elementsContainer[index]
    }

    private fun size(elementsContainer: ElementsContainer<T>) = elementsContainer.end - elementsContainer.start + 1
}
