package com.home.utils.elements

import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.type.IntType
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference

/**
 * Test for [Matrix].
 */
class MatrixTest {

    /**
     * iterating over all possible indexes test checks logic of [Matrix.set].
     */
    @Test
    fun testSet() {
        val N = 5
        for (i in 1..N)
            for (j in 1..N)
                for (k in 1..N)
                    for (l in 1..N) {
                        this.testSetLogic(intArrayOf(i, j, k, l), i-1, j-1, k-1, l-1)
                    }

    }

    private fun testSetLogic(dimension: IntArray, vararg indexes: Int) {
        val expectedMatrix = nDimArray(dimension)
        this.set(expectedMatrix, 1, *indexes)
        var matrix = this.matrix(expectedMatrix, *indexes)
        matrix.set(1, *indexes)
        val expectedValue = this.get<Int>(expectedMatrix, *indexes)
        val actualValue = matrix.get(*indexes)
        Assert.assertEquals(expectedValue, actualValue)
        val dash = "---------"
        val passTitle = "$dash PASS $dash"

        println(passTitle)
        println("Dim: ${dimension.joinToString(truncated = "")}")
        println("Ind: ${indexes.joinToString(truncated = "")}")
        println((1..passTitle.length).joinToString(separator = "", truncated = "") { "-" })
        println("")
    }

    private fun<T> get(expectedMatrix: Array<*>, vararg indexes: Int): T {
        var result = AtomicReference<Array<*>>()
        result.set(expectedMatrix[indexes[0]] as Array<*>?)
        for (i in 1 until indexes.size - 1) {
            result.set(result.get()[indexes[i]] as Array<*>);
        }
        return (result.get() as Array<T>)[indexes[indexes.size - 1]]
    }

    private fun <T> set(array: Array<*>, value: T, vararg indexes: Int) {
        var result = AtomicReference<Array<*>>()
        result.set(array[indexes[0]] as Array<*>?)
        for (i in 1 until indexes.size - 1) {
            result.set(result.get()[indexes[i]] as Array<*>);
        }
        (result.get() as Array<T>)[indexes[indexes.size - 1]] = value
    }

    private fun matrix(expectedMatrix: Array<*>, vararg expectedValueIndexes: Int): Matrix<Int> {
        val sizes = arrayListOf(*expectedValueIndexes.toTypedArray())
        var any: Array<*> = expectedMatrix;
        var r = 0
        while(any is Array<*> && any.isNotEmpty()) {
            sizes[r] = any.size
            val any0 = any[0]
            if (any0 !is Array<*>) {
                sizes[r] = any.size
                any = arrayOf<Any>()
            } else {
                any = any0
                r++
            }
        }

        return Matrix(IntType, *sizes.toIntArray())
    }


    private fun nDimArray(sizes: IntArray): Array<*> {
        val elements = (1..sizes[sizes.size - 1]).map { 0 }.toTypedArray()
        var result: Array<*> = arrayOf(*elements)

        for (n in sizes.size - 1 downTo 1) {
            val amount = sizes[n-1]
            result = arr(result, amount)
        }
        return result;
    }

    private fun arr(arr:Array<*>, n: Int): Array<*> {
        val result: ArrayList<Array<*>> = ArrayList()
        for (i in 1..n) {
            result.add(arr)
        }
        return result.toTypedArray()

    }

}
