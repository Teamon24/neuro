package com.home.utils.elements

import com.home.utils.operators.get
import com.home.utils.operators.set

open class Vector<T> : Typed<T> {
    
    val elements: ArrayList<T>

    val size: Int

    constructor(size: Int, type: Type<T>) : super(type) {
        this.elements = ArrayList(size)
        this.size = size
        init()
    }

    constructor(vector: Vector<T>,
                type: Type<T>) : super(type)
    {
        this.elements = vector.elements
        this.size = this.elements.size
        init()
    }

    constructor(collection: Collection<T>,
                type: Type<T>) : super(type)
    {
        this.elements = ArrayList(collection)
        this.size = this.elements.size
        init()
    }

    constructor(array: Array<T>, type: Type<T>) : super(type) {
        this.elements = ArrayList(array.toList())
        this.size = this.elements.size
    }

    private fun init() {
        (0 until this.size).forEach {
            this.elements.add(super.type.init())
        }
    }

    fun forEach(transform: (T) -> T): Vector<T> {
        val size = this.size
        val result: Vector<T> = super.type.vector(size)
        (0 until size).forEach { i ->
            result[i] = transform(this[i])
        }
        return result
    }
}



