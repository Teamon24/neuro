package com.home.utils

import com.google.common.collect.Lists
import com.home.utils.elements.latest.MatrixND
import com.home.utils.operators.allIndexesCombos
import java.util.concurrent.atomic.AtomicReference

/**
 * Works with right arrays.
 */
object ArrayUtils {

    inline fun <reified T> nDimArray(sizes: IntArray, init: () -> T): Array<*> {
        val elements = (1..sizes[sizes.size - 1]).map { init() }.toTypedArray()
        var result: Array<*> = arrayOf(*elements)

        for (n in sizes.size - 1 downTo 1) {
            val amount = sizes[n-1]
            result = arr(result, amount)
        }
        return result;
    }

    inline fun <reified T> nDimArray(matrix: MatrixND<T>): Array<*> {
        val nDimArray = nDimArray(matrix.sizes) { 0 }
        val allIndexesCombos = matrix.allIndexesCombos()
        for (indexes in allIndexesCombos) {
            val value = matrix.getAt(indexes)
            this.set(nDimArray, value, indexes)
        }
        return nDimArray
    }

    fun getSizes(array: Array<*>): IntArray {
        val sizes = ArrayList<Int>();
        var any = array[0]
        if (any.isNotArray()) {
            sizes.add(array.size)
            return sizes.toIntArray()
        } else {
            sizes.add(array.size)
            while (any is Array<*>) {
                val size = any.size
                any = any[0]
                if (any.isNotArray()) {
                    sizes.add(size)
                    return sizes.toIntArray()
                } else {
                    sizes.add(size)
                }
            }
        }

        return sizes.toIntArray()
    }

    fun println(array: Array<*>) {
        val allIndexesCombos = array.allIndexesCombos()
        val allIndexesDropLast = allIndexesCombos.map { it.dropLast(1) }.distinct()
        allIndexesDropLast.forEach { indexes ->
            val lastArray = getLastArrayOf(array, indexes.toIntArray())
            println("$indexes:${lastArray.contentToString()}" )
        }
    }

    fun getLastArrayOf(nDimArray: Array<*>, indexes: IntArray): Array<*> {
        val sizes = getSizes(nDimArray)
        val sizesWithoutLast = sizes.dropLast(1).toIntArray()
        Thrower.throwIfAnyOverBound(indexes, sizesWithoutLast)

        val any = nDimArray[indexes[0]]

        if (any.isNotArray()) {
            throw RuntimeException("It is not possible to get array of one-dimensional array")
        }

        if (any.isArray()) {
            return any as Array<*>
        }

        val result = AtomicReference<Array<*>>()
        if (any.isNDimArray()) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>);
            }
            return result.get() as Array<*>
        }

        throw RuntimeException("Unexpected case")
    }

    fun get(nDimArray: Array<*>, indexes: IntArray): Any? {
        Thrower.throwIfAnyNegative(indexes)
        val result = AtomicReference<Array<*>>()
        val any = nDimArray[indexes[0]]

        if (any.isNDimArray()) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>);
            }
            return (result.get() as Array<*>)[indexes[indexes.size - 1]]
        }

        if (any.isNotArray()) {
            Thrower.throwIfBelowBound(1, indexes.size)
            val index = indexes[0]
            Thrower.throwIfOverBound(index, nDimArray.size - 1)
            return nDimArray[index]
        }

        throw RuntimeException("Unexpected case")
    }

    inline fun<reified T> set(array: Array<*>, value: T, indexes: List<Int>) {
        val result = AtomicReference<Array<*>>()
        val any = array[indexes[0]]
        if (any is Array<*>) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>);
            }
            (result.get() as Array<T>)[indexes[indexes.size - 1]] = value
            return
        }

        throw RuntimeException("Unexpected case")
    }

    fun arr(arr:Array<*>, n: Int): Array<*> {
        val result: ArrayList<Array<*>> = ArrayList()
        for (i in 1..n) {
            result.add(arr)
        }
        return result.toTypedArray()
    }
}

fun Any?.isArray() = this is Array<*> && this[0].isNotArray()
fun Any?.isNDimArray() = this is Array<*> && this[0].isArray()
fun Any?.isNotArray() = this !is Array<*>
fun Array<*>.allIndexesCombos(): List<List<Int>> = Lists.cartesianProduct(ArrayUtils.getSizes(this).map { (0 until it).toList() })