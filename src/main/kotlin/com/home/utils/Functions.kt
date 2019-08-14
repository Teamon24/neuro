package com.home.utils

import com.home.utils.elements.latest.Matrix

/**
 * R - reducer; P - product; S - summation
 */
inline fun<reified T> IntRange.R(arr: Array<T>, op: (T, T) -> T) = this.map { arr[it] }.reduce { acc, next ->  op(acc,next) }
inline fun<reified T> IntRange.S(arr: Array<T>, plus: (T, T)-> T) = this.R(arr, plus)
inline fun<reified T> IntRange.S(underSum: (Int) -> T, plus: (T, T) -> T) = this.R(this.map { underSum(it) }.toTypedArray(), plus)
inline fun<reified T> IntRange.P(arr: Array<T>, times: (T, T)-> T) = this.R(arr, times)
inline fun<reified T> IntRange.P(underProduct: (Int) -> T, times: (T, T)-> T) = this.R(this.map { underProduct(it) }.toTypedArray(), times)


/**
 * R - Reducer.
 * @param reducer function to be applied to reduce integer sequence to value.
 */
inline fun IntRange.R(toReduce: IntArray, reducer: (Int, Int) -> Int) = this.map { toReduce[it] }.reduce { acc, next -> reducer(acc, next) }


/**
 * S - Summation.
 */
inline fun IntRange.S(underSum: (Int) -> Int): Int {
    val underSumValues = this.map { underSum(it) }
    return this.R(underSumValues.toIntArray(), Int::plus)
}
fun S(arr: IntArray) = (0 until arr.size).R(arr, Int::plus)


/**
 * P - Product.
 */
inline fun IntRange.P(underProduct: (Int) -> Int): Int {
    val underProductValues = this.map { underProduct(it) }
    return this.R(underProductValues.toIntArray(), Int::times)
}
fun IntRange.P(arr: IntArray) = this.R(arr, Int::times)
fun P(arr: IntArray) = (0 until arr.size).R(arr, Int::times)




fun IntArray.deleteAt(index: Int): IntArray {
    val before = this.copyOf().sliceArray(0 until index)
    val after = this.copyOf().sliceArray(index + 1..this.size)
    return before + after
}

operator fun <T> Matrix<T>.get(index: Int): Matrix<T> {
    val newSizes = this.sizes.deleteAt(index)

    val newMatrix = Matrix(this.type, *newSizes)
    for (d in 0 until newSizes.size) {
        var size = newSizes[d]
    }
    return newMatrix
}