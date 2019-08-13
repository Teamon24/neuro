package com.home.utils.operators

operator fun IntRange.minus(shift: Int) = (this.first - shift)..(this.last - shift)
operator fun<T> T.plus(array: Array<T>) = arrayOf(this, array)
operator fun Int.plus(array: IntArray) = intArrayOf(this, *array)





