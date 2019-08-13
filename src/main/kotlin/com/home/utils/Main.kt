package com.home.utils

import com.home.Matrix
import com.home.utils.elements.type.IntType

fun main() {
    val n = 3
    val m = Matrix(IntType, n, n, n)

    for (i in 9 until 12) m.set(1, i)

    for (i in 0 until n) {
        for (j in 0 until n) {
            for (k in 0 until n) {
                println("($i:$j:$k) = ${m.get(i, j, k)}")
            }
        }
    }
}