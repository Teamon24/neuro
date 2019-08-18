package com.home.utils.elements.latest

import com.home.utils.Thrower
import com.home.utils.elements.type.Type
import com.home.utils.elements.type.Typed

class Vector<T> : Typed<T> {

    private val container: Container<T>
    val size: Int

    constructor(type: Type<T>, size: Int) : super(type) {
        this.container = Matrix(type, 1, size).container
        this.size = size(this.container)
    }

    constructor(container: Container<T>) : super(container.type) {
        this.container = container
        this.size = size(this.container)
    }

    operator fun set(index: Int, value: T) {
        Thrower.throwIfNegative(index)
        Thrower.throwIfOverBound(index, this.size - 1)
        this.container[index] = value
    }


    operator fun get(index: Int): T {
        Thrower.throwIfNegative(index)
        Thrower.throwIfOverBound(index, this.size - 1)
        return this.container[index]
    }

    private fun size(container: Container<T>) = container.end - container.start + 1
}
