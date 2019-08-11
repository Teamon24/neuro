package com.home.utils

import com.home.utils.elements.Matrix2D
import com.home.utils.elements.Type
import com.home.utils.operators.get
import com.home.utils.operators.strass
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random

fun main() {

    val initDouble: () -> Double = { 0.0 }
    val N = 10
    val M = N
    fun round(double: () -> Double) = BigDecimal(double()).setScale(2, RoundingMode.HALF_EVEN).toDouble()

    val double = { round { Random.nextDouble() } }
    val type = Type(initDouble, { d1, d2 -> d1 * d2 }, { d1, d2 -> d1 - d2 }, { d1, d2 -> d1 + d2 })
    val A = type.matrix(N)
    val B = type.matrix(N)
    Matrix2D.fill(double, A)
    Matrix2D.fill(double, B)
    val R = A strass B

    println()
    println("C: ")
    println()
    for (i in 0 until N) {
        for (j in 0 until N)
            print("${R[i][j]} ")
        println()
    }

}