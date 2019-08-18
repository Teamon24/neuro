package com.home.utils.functions

import com.home.utils.Thrower


fun IntArray.only(amount: Int, predicate: (Int) -> Boolean): Boolean {
    Thrower.throwIfSizeIsLessThanAmount(this.size, amount)
    return this.filter { predicate(it) }.count() == amount
}

fun IntArray.min(amount: Int, predicate: (Int) -> Boolean): Boolean {
    Thrower.throwIfSizeIsLessThanAmount(this.size, amount)
    return this.filter { predicate(it) }.count() >= amount
}

fun IntArray.max(amount: Int, predicate: (Int) -> Boolean): Boolean {
    Thrower.throwIfSizeIsLessThanAmount(this.size, amount)
    return this.filter { predicate(it) }.count() <= amount
}

fun IntArray.any(predicate: (Int) -> Boolean) = this.min(1, predicate)

fun IntArray.all(predicate: (Int) -> Boolean) = this.min(this.size, predicate)

fun IntArray.no(value: Int) = min(0) { it == value }

fun IntArray.deleteAt(index: Int): IntArray {
    if (index == 0) {
        val elements = this.toTypedArray().drop(1)
        return elements.toIntArray()
    }

    val before = this.copyOf().sliceArray(0 until index + 1)
    val after = this.copyOf().sliceArray((index + 1) until this.size)

    return before + after
}


private fun IntArray.map(t: Int, operation: (Int, Int) -> Int) = this.map { operation(it, t) }.toIntArray()
private fun IntArray.addition(t: Int, op: (Int, Int) -> Int) = if (t != 0) this.map(t, op) else this
private fun IntArray.multiply(t: Int, op: (Int, Int) -> Int): IntArray {
    if (t == 0) return this.map(0, op)
    return if (t != 1) this.map(t, op) else this
}

infix fun IntArray.upOn(t: Int) = addition(t, Int::plus)
infix fun IntArray.downOn(t: Int) = addition(t, Int::minus)
infix fun IntArray.divideOn(t: Int) = if (t != 0) multiply(t, Int::div) else throw DivideByZeroException("Divided by zero")
infix fun IntArray.timesOn(t: Int) = multiply(t, Int::times)

class DivideByZeroException(message: String) : Throwable(message)

