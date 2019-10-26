package com.home.utils.functions

import kotlin.random.Random

/**
 * R - reducer; P - product; S - summation
 */
inline fun<reified T> IntRange.R(arr: Array<T>, op: (T, T) -> T) = this.map { arr[it] }.reduce { acc, next ->  op(acc,next) }
inline fun<reified T> IntRange.Sum(arr: Array<T>, plus: (T, T)-> T) = this.R(arr, plus)
inline fun<reified T> IntRange.Sum(underSum: (Int) -> T, plus: (T, T) -> T) = this.R(this.map { underSum(it) }.toTypedArray(), plus)
inline fun<reified T> IntRange.Prod(arr: Array<T>, times: (T, T)-> T) = this.R(arr, times)
inline fun<reified T> IntRange.Prod(underProduct: (Int) -> T, times: (T, T)-> T) = this.R(this.map { underProduct(it) }.toTypedArray(), times)


/**
 * R - Reducer.
 * @param reducer function to be applied to reduce integer sequence to value.
 */
inline fun IntRange.R(toReduce: IntArray, reducer: (Int, Int) -> Int) = this.map { toReduce[it] }.reduce { acc, next -> reducer(acc, next) }


/**
 * S - Summation.
 */
inline fun IntRange.Sum(underSum: (Int) -> Int): Int {
    val underSumValues = this.map { underSum(it) }
    return this.R(underSumValues.toIntArray(), Int::plus)
}

fun Sum(arr: IntArray) = (0 until arr.size).R(arr, Int::plus)


/**
 * P - Product.
 */
inline fun IntRange.Prod(underProduct: (Int) -> Int): Int {
    val underProductValues = this.map { underProduct(it) }
    return this.R(underProductValues.toIntArray(), Int::times)
}

fun IntRange.Prod(arr: IntArray) = this.R(arr, Int::times)
fun Prod(arr: IntArray) = (0 until arr.size).R(arr, Int::times)


/**
 * USING ONLY ONE [IntRange] OBJECT IN DOUBLE [for] MAKES COUNTER COUNT FASTER IN TWO TIMES.
 */
operator fun IntRange.invoke(forBody: IntRange.() -> Unit) {
    val initial = this.i
    this.i = this.first
    this.forEach { _ -> forBody(); this.i++ ; }
    this.i = initial
}

var IntRange.i: Int by Property("i") { 0 }
var IntRange.random: Random by RandomProp("random") { Random }

fun IntRange.randomExclusive() = this.random.nextInt(this.first, this.last)
fun IntRange.randomInclusive() = this.random.nextInt(this.first, this.last + 1)

