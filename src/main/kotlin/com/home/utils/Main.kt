package com.home.utils

import com.home.utils.elements.Matrix2D
import com.home.utils.elements.Type
import com.home.utils.operators.get
import com.home.utils.operators.strass
import kotlin.random.Random

fun main() {

    val initShort: () -> Short = { 0.toShort() }
    val N = 10
    val M = N
    val short = { Random.nextInt().toShort() }
    val type = Type(initShort, Short::times, Short::minus, Short::plus)
    val A = type.matrix(N)
    val B = type.matrix(N)
    Matrix2D.fill(short, A)
    Matrix2D.fill(short, B)
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