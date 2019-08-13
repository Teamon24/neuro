package com.home.utils.elements

import com.home.utils.elements.type.Type

class Row<T> : Vector<T> {
    constructor(size: Int, type: Type<T>) : super(size, type)
    constructor(vector: Vector<T>, type: Type<T>) : super(vector, type)
}