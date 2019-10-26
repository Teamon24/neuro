package com.home.utils

import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.type.Type
import com.home.utils.operators.allIndexesCombos
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KClass

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

    inline fun <reified T> nDimArray(matrix: Matrix<T>): Array<*> {
        val nDimArray = nDimArray(matrix.sizes) { 0 }
        val allIndexesCombos = matrix.allIndexesCombos()
        val type = matrix.type
        for (indexes in allIndexesCombos) {
            val value = matrix.getAt(indexes)
            this.set(nDimArray, value, indexes)
        }
        return nDimArray
    }

    inline fun<reified T : Any> get(expectedMatrix: Array<*>, type: KClass<T>, vararg indexes: Int): T {
        val result = AtomicReference<Array<*>>()
        val any = expectedMatrix[indexes[0]]
        if (any is Array<*>) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>);
            }
            return (result.get() as Array<T>)[indexes[indexes.size - 1]]
        }

        if (expectedMatrix[indexes[0]] is T) {
            return expectedMatrix[indexes[indexes.size - 1]] as T
        }

        throw RuntimeException("Unexpected result: ")
    }

    inline fun<reified T> set(expectedMatrix: Array<*>, value: T, indexes: List<Int>) {
        val result = AtomicReference<Array<*>>()
        val any = expectedMatrix[indexes[0]]
        if (any is Array<*>) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>);
            }
            (result.get() as Array<T>)[indexes[indexes.size - 1]] = value
            return
        }

        throw RuntimeException("Unexpected result: ")
    }

    inline fun<reified T : Any> get(expectedMatrix: Array<*>, c: KClass<T>, indexes: List<Int>, sizes: IntArray): T {
        Thrower.throwIfAnyNegative(indexes)
        Thrower.throwIfWrongDimension(indexes, sizes)
        val result = AtomicReference<Array<*>>()
        val any = expectedMatrix[indexes[0]]
        if (any is Array<*>) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>)
            }
            return (result.get() as Array<T>)[indexes[indexes.size - 1]]
        }

        if (expectedMatrix[indexes[0]] is T) {
            return expectedMatrix[indexes[indexes.size - 1]] as T
        }

        throw RuntimeException("Unexpected result: ")
    }

    fun arr(arr:Array<*>, n: Int): Array<*> {
        val result: ArrayList<Array<*>> = ArrayList()
        for (i in 1..n) {
            result.add(arr)
        }
        return result.toTypedArray()

    }
}