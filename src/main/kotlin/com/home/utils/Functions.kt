package com.home.utils

import com.home.utils.operators.minus

/**
 * R - reducer; P - product; S - summation
 */

fun<T> IntRange.R(arr: Array<T>, op: (T, T) -> T) = map { arr[it] }.reduce { acc, next ->  op(acc,next) }
fun<T> IntRange.S(arr: Array<T>, plus: (T, T)-> T) = R(arr, plus)
fun<T> IntRange.P(arr: Array<T>, times: (T, T)-> T) = R(arr, times)


fun IntRange.R(arr: IntArray, op: (Int, Int) -> Int) = map { arr[it] }.reduce { acc, next -> op(acc,next) }

fun IntRange.S(underSum: (Int) -> Int) = R(map { underSum(it) }.toIntArray(), Int::plus)
fun IntRange.S1(underSum: (Int) -> Int): Int {
    val range1 = this - 1
    return range1.R(range1.map { underSum(it) }.toIntArray(), Int::plus)
}

fun IntRange.P(underProduct: (Int) -> Int) = R(map { underProduct(it) }.toIntArray(), Int::times)
fun IntRange.P(arr: IntArray) = R(arr, Int::times)
fun P(arr: IntArray) = (0 until arr.size).R(arr, Int::times)

fun IntRange.P1(underProduct: (Int) -> Int): Int {
    val intRange = this - 1
    return intRange.R(intRange.map { underProduct(it) }.toIntArray(), Int::times)
}
fun IntRange.P1(arr: IntArray) = (this - 1).R(arr, Int::times)