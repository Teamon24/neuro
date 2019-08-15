package com.home.utils

operator fun Int.plus(collection: Collection<Int>) = this.plus<Int>(collection)

operator fun <T> T.plus(collection: Collection<T>): List<T> {
    val arrayList = ArrayList<T>()
    arrayList.add(this)
    arrayList.addAll(collection)
    return arrayList
}

fun <E> List<E>.inverse(): List<E> {
    val inversed = ArrayList<E>()
    for (i in this.size - 1 downTo 0) {
        inversed.add(this[i])
    }
    return inversed
}

fun <T> Collection<T>.op(t: T, operation: (T,T) -> T) = this.map { operation(it, t) }
infix fun Collection<Int>.upOn(t: Int) = this.op(t, Int::plus)
fun Collection<Int>.decreaseOn(t: Int) = this.op(t, Int::minus)

fun IntArray.op(t: Int, operation: (Int, Int) -> Int) = this.map { operation(it, t) }.toIntArray()
infix fun IntArray.upOn(t: Int) = this.op(t, Int::plus)
fun IntArray.decreaseOn(t: Int) = if (t != 0) this.op(t, Int::minus) else this

fun List<Int>.op(t: Int, operation: (Int, Int) -> Int) = this.map { operation(it, t) }.toIntArray()
infix fun List<Int>.upOn(t: Int) = this.op(t, Int::plus)
fun List<Int>.decreaseOn(t: Int) = this.op(t, Int::minus)
