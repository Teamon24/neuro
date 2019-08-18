package com.home.utils.functions


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

private fun <T> Collection<T>.map(t: T, operation: (T, T) -> T) = this.map { operation(it, t) }
private fun List<Int>.map(t: Int, operation: (Int, Int) -> Int) = this.map { operation(it, t) }.toIntArray()

infix fun Collection<Int>.upOn(t: Int) = this.map(t, Int::plus)
infix fun List<Int>.upOn(t: Int) = this.map(t, Int::plus)

infix fun Collection<Int>.downOn(t: Int) = this.map(t, Int::minus)
infix fun List<Int>.downOn(t: Int) = this.map(t, Int::minus)

fun <T> Collection<Collection<T>>.exlude(predicate: (Collection<T>) -> Boolean) = this.filter { !predicate(it) }.toList()

