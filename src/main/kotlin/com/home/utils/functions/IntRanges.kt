package com.home.utils.functions

import javax.swing.tree.TreeNode

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


/**
 * USING ONLY ONE [IntRange] OBJECT IN DOUBLE [for] MAKES COUNTER COUNT FASTER IN TWO TIMES.
 */
operator fun IntRange.invoke(forBody: IntRange.() -> Unit) {
    val initial = this.i
    this.i = this.first - 1
    this.forEach { _ -> this.i++ ; forBody() }
    this.i = initial
}

var IntRange.i: Int by Property("i") { 0 }