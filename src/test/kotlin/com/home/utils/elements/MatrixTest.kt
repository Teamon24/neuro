package com.home.utils.elements

import com.google.common.collect.Lists
import com.home.utils.*
import com.home.utils.elements.latest.MatrixNdim
import com.home.utils.elements.type.DoubleType
import com.home.utils.operators.allIndexesCombos
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random
import kotlin.reflect.KClass

/**
 * Test for [MatrixNdim].
 */
class MatrixTest {

    /**
     * Iterating over all possible dimensions
     * test creates matrix
     * and checks logic of [MatrixNdim.set] via random indexes.
     */
    @Test
    fun testSet() {
        val type = DoubleType
        val cartesians = dimensions(3, 15)

        for (cartesian in cartesians) {
            for (dimension in cartesian) {
                val sizes = dimension.toIntArray()

                val expectedMatrix = ArrayUtils.nDimArray(sizes) { 0.0 }
                val actualMatrix = MatrixNdim(type, *sizes)

                val indexes = randomIn(sizes)
                this.set(expectedMatrix, 1.0, *indexes)
                actualMatrix.set(1.0, *indexes)

                val expectedValue = this.get(expectedMatrix, type.clazz(), *indexes)
                val actualValue = actualMatrix.get(*indexes)

                Assert.assertTrue(expectedValue == actualValue)
                this.printResult(sizes, indexes)
            }
        }
    }

    @Test
    fun testTranspose() {
        val original = MatrixNdim(DoubleType, 3, 3, 3, 3)
        original.set(1.0, 0, 0, 0, 0)
        original.set(2.0, 0, 1, 0, 1)
        original.set(3.0, 0, 2, 0, 2)
        val transposed = original.transpose()

        val allIndexesCombos = original.allIndexesCombos()

        for (indexes in allIndexesCombos) {
            val right = indexes.toIntArray()
            val inversed = indexes.inverse().toIntArray()
            val originalValue = original.get(*right)
            val transposedValue = transposed.get(*inversed)
            Assert.assertEquals(originalValue, transposedValue, 1E-4)
        }

    }

    /**
     * @param dimension amount of matrix sizes.
     * @param max maximal length of each size in dimension.
     */
    private fun dimensions(dimension: Int, max: Int): List<List<List<Int>>> {
        val list = arrayListOf<List<List<Int>>>()
        (1..dimension) {
            val list1 = arrayListOf<List<Int>>()
            (1..i) {
                list1.add((1..max).toList())
            }
            list.add(list1)
        }
        val cartesians = arrayListOf<List<List<Int>>>()

        for (ds in list) {
            val cartesian = Lists.cartesianProduct(ds)
            cartesians.add(cartesian)
        }

        return cartesians
    }

    private fun randomIn(sizes: IntArray): IntArray {
        val random = Random(sizes[0])
        return sizes.map { random.nextInt(0, it) }.toIntArray()
    }

    private fun printResult(dimensions: IntArray, indexes: IntArray) {

        val dash = "---------"
        val passTitle = "$dash PASS $dash"

        println(passTitle)
        println("Dim: ${dimensions.joinToString(truncated = "")}")
        println("Ind: ${indexes.joinToString(truncated = "")}")
        println((1..passTitle.length).joinToString(separator = "", truncated = "") { "-" })
        println("")
    }

    private inline fun<reified T : Any> get(expectedMatrix: Array<*>, tClass: KClass<T>, vararg indexes: Int): T {
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

    private inline fun <reified T> set(array: Array<*>, value: T, vararg indexes: Int) {
        val result = AtomicReference<Array<*>>()
        val any = array[indexes[0]]

        if (any is Array<*>) {
            result.set(any as Array<*>?)
            for (i in 1 until indexes.size - 1) {
                result.set(result.get()[indexes[i]] as Array<*>);
            }
            (result.get() as Array<T>)[indexes[indexes.size - 1]] = value
        }

        if ( array[indexes[0]] is T) {
            (array as Array<T>) [indexes[indexes.size - 1]] = value
        }

    }
}

