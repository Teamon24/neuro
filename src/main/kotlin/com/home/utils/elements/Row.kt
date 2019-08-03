package com.home.utils.elements

class Row<T> : Vector<T> {
    constructor(size: Int, type: Type<T>) : super(size, type)
    constructor(vector: Vector<T>, type: Type<T>) : super(vector, type)
}