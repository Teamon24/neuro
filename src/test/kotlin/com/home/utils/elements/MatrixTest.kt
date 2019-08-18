package com.home.utils.elements

import com.google.common.collect.Lists
import com.home.utils.*
import com.home.utils.elements.latest.*
import com.home.utils.elements.type.Doubles
import com.home.utils.elements.type.Integers
import com.home.utils.functions.*
import com.home.utils.operators.allIndexesCombos
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random
import kotlin.reflect.KClass

/**
 * Test for [Matrix].
 */
class MatrixTest {

    val type = Integers

    /**
     * Iterating over all possible dimensions
     * test creates matrix
     * and checks logic of [Matrix.set] via random indexes.
     */
    @Test
    fun testSet() {
        val cartesians = cartesians(3, 15)

        for (dimensions in cartesians) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()

                val expectedMatrix = ArrayUtils.nDimArray(sizes) { 0 }
                val actualMatrix = matrix(sizes)

                val indexes = randomIn(sizes)
                this.set(expectedMatrix, 1, *indexes)
                actualMatrix.set(1, *indexes)

                val expectedValue = this.get(expectedMatrix, type.clazz(), *indexes)
                val actualValue = actualMatrix.getAt(*indexes)

                Assert.assertTrue(expectedValue == actualValue)
                this.printResult(sizes, indexes, "PASS")
            }
        }
    }

    /**
     * Iterating over all possible dimensions
     * test creates matrix
     * and checks logic of [Matrix2D.set], [Vector.set] via random indexes.
     */
    @Test
    fun testSets() {
        val cartesians = this.get3DMatricesDims(cartesians(3, 15))


        for (dimensions in cartesians) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val indexes = randomIn(sizes)
                val expectedValue = Random.nextInt()
                val (i, j, k) = Triple(0, indexes[1], indexes[2])
                try {
                    val matrix3D = matrix3D(sizes)
                    val matrix2D = matrix3D[i]
                    val vector = matrix2D[j]
                    vector[k] = expectedValue
                    Assert.assertTrue(expectedValue == matrix3D[i][j][k])
                    this.printResult(sizes, indexes, "PASS")
                } catch (e: Exception) {
                    this.printResult(sizes, indexes, "ERROR")
                    throw e
                }
            }
        }
    }

    @Test
    fun testIsScalar() {
        val cartesians = cartesians(7, 1)
        for (dimensions in cartesians) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val matrix = matrix(sizes)
                Assert.assertTrue(matrix.isScalar())
                Assert.assertFalse(matrix.isVector())
                Assert.assertFalse(matrix.isMatrix(sizes.size))
            }
        }
    }

    @Test
    fun testIsVector() {
        val sizesByDimensions = this.getVectorsDims(cartesians(5, 3))

        for (dimensions in sizesByDimensions) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val matrix = matrix(sizes)
                Assert.assertFalse(matrix.isScalar())
                Assert.assertTrue(matrix.isVector())
                Assert.assertFalse(matrix.isMatrix(sizes.size))
                printResult(sizes, intArrayOf(), "PASS")
            }
        }
    }

    @Test
    fun testIsMatrix() {
        val sizesByDimensions = this.getMatricesDims(cartesians(5, 3))

        for (dimensions in sizesByDimensions) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val matrix = matrix(sizes)
                Assert.assertFalse(matrix.isScalar())
                Assert.assertFalse(matrix.isVector())
                Assert.assertTrue(matrix.isMatrix(sizes.size))
                printResult(sizes, intArrayOf(), "PASS")
            }
        }
    }

    private fun matrix(sizes: IntArray) = Matrix(type, *sizes)
    private fun matrix2D(sizes: IntArray) = Matrix2D(type, sizes[0], sizes[1])
    private fun matrix3D(sizes: IntArray) = Matrix3D(type, sizes[0], sizes[1], sizes[2])

    private fun getVectorsDims(cartesians: List<MutableList<List<Int>>>): ArrayList<MutableList<List<Int>>> {
        val nonOneCartesians = get(cartesians) { sizes -> sizes.size > 1 && sizes.only(1) { it > 1 }}
        return nonOneCartesians
    }

    private fun getMatricesDims(cartesians: List<MutableList<List<Int>>>): ArrayList<MutableList<List<Int>>> {
        val nonOneCartesians = get(cartesians) { sizes -> sizes.size > 1 && sizes.min(2) {it > 1}}
        return nonOneCartesians
    }

    private fun get3DMatricesDims(cartesians: List<MutableList<List<Int>>>): ArrayList<MutableList<List<Int>>> {
        val nonOneCartesians = get(cartesians) { sizes -> sizes.size == 3 && sizes.min(3) {it > 1}}
        return nonOneCartesians
    }

    private fun get(cartesians: List<MutableList<List<Int>>>,
                    condition: (IntArray) -> Boolean): ArrayList<MutableList<List<Int>>> {
        val nonOneCartesians = arrayListOf<MutableList<List<Int>>>()
        for (cartesian in cartesians) {
            val nonOneCartesian = arrayListOf<List<Int>>()
            for (dimension in cartesian) {
                val sizes = dimension.toIntArray()
                if (condition(sizes)) {
                    nonOneCartesian.add(dimension)
                }
            }
            nonOneCartesian.isNotEmpty().then { nonOneCartesians.add(nonOneCartesian) }
        }
        return nonOneCartesians
    }

    @Test
    fun testTranspose() {
        val original = Matrix(Doubles, 3, 3, 3, 3)
        original.set(1.0, 0, 0, 0, 0)
        original.set(2.0, 0, 1, 0, 1)
        original.set(3.0, 0, 2, 0, 2)
        val transposed = original.transpose()

        val allIndexesCombos = original.allIndexesCombos()

        for (indexes in allIndexesCombos) {
            val right = indexes.toIntArray()
            val inversed = indexes.inverse().toIntArray()
            val originalValue = original.getAt(*right)
            val transposedValue = transposed.getAt(*inversed)
            Assert.assertEquals(originalValue, transposedValue, 1E-4)
        }

    }

    /**
     * @param dimension amount of matrix sizes.
     * @param size maximal length of each size in dimension.
     * @return all possibles combinations of dimensions
     */
    private fun cartesians(dimension: Int, size: Int): List<MutableList<List<Int>>> {
        val list = arrayListOf<List<List<Int>>>()
        (1..dimension) {
            val list1 = arrayListOf<List<Int>>()
            (1..i) {
                list1.add((1..size).toList())
            }
            list.add(list1)
        }
        val cartesians = arrayListOf<MutableList<List<Int>>>()

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

    private fun printResult(dimensions: IntArray, indexes: IntArray, code: String) {

        val dash = "---------"
        val passTitle = "$dash $code $dash"

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

