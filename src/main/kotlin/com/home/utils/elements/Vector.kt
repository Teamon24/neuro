package com.home.utils.elements

import com.home.utils.operators.get
import com.home.utils.operators.set

open class Vector<T> : Typed<T> {
    
    val elements: ArrayList<T>

    lateinit var size: () -> Int

    constructor(size: Int, type: Type<T>) : super(type) {
        this.elements = ArrayList(size)
        initSize()
    }

    constructor(vector: Vector<T>,
                type: Type<T>) : super(type)
    {
        this.elements = vector.elements
        initSize()
    }

    constructor(collection: Collection<T>,
                type: Type<T>) : super(type)
    {
        this.elements = ArrayList(collection)
        initSize()
    }

    constructor(array: Array<T>, type: Type<T>) : super(type) {
        this.elements = ArrayList(array.toList())
        initSize()
    }

    private fun initSize() {
        this.size = { this.elements.size }
    }

    fun forEach(transform: (T) -> T): Vector<T> {
        val size = this.size()
        val result: Vector<T> = this.type.vector(size)
        (0 until size).forEach { i ->
            result[i] = transform(this[i])
        }
        return result
    }
}



