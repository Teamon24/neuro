package com.home.utils.elements

import com.home.utils.ArrayUtils
import com.home.utils.elements.MatrixTestUtils.cartesians
import com.home.utils.elements.MatrixTestUtils.get3DMatricesDims
import com.home.utils.elements.MatrixTestUtils.getMatricesDims
import com.home.utils.elements.MatrixTestUtils.getVectorsDims
import com.home.utils.elements.MatrixTestUtils.matrix
import com.home.utils.elements.MatrixTestUtils.matrix3D
import com.home.utils.elements.latest.*
import com.home.utils.elements.type.Integers
import com.home.utils.functions.i
import com.home.utils.functions.inverse
import com.home.utils.functions.invoke
import com.home.utils.operators.allIndexesCombos
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random
import kotlin.reflect.KClass

val TYPE = Integers

/**
 * Test for [Matrix].
 */
class MatrixTest {


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
                val actualMatrix = matrix(TYPE, sizes)

                val indexes = randomIn(sizes)
                this.set(expectedMatrix, 1, *indexes)
                actualMatrix.set(1, *indexes)

                val expectedValue = this.get(expectedMatrix, TYPE.clazz(), *indexes)
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
        val cartesians = get3DMatricesDims(cartesians(3, 15))


        for (dimensions in cartesians) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val indexes = randomIn(sizes)
                val expectedValue = Random.nextInt()
                val (i, j, k) = Triple(0, indexes[1], indexes[2])
                try {
                    val matrix3D = matrix3D(TYPE, sizes)
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

    /**
     * Iterating over all possible dimensions
     * test creates matrix
     * and cashing mechanism of internal matrix elements.
     */
    @Test
    fun testCash() {
        val cartesians = cartesians(3, 15)
        val vectorDimensions = getVectorsDims(cartesians)
        val matricesDimensions = getMatricesDims(cartesians)
        for (dimensions in vectorDimensions + matricesDimensions) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val matrix = matrix(TYPE, sizes)
                (0 until sizes.size) {
                    val size = sizes[i]
                    val index = size - 1
                    val matrix1 = matrix[index]
                    val matrix2 = matrix[index]
                    Assert.assertTrue(matrix1 === matrix2)
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
                val matrix = matrix(TYPE, sizes)
                Assert.assertTrue(matrix.isScalar())
                Assert.assertFalse(matrix.isVector())
                Assert.assertFalse(matrix.isMatrix(sizes.size))
            }
        }
    }

    @Test
    fun testIsVector() {
        val sizesByDimensions = getVectorsDims(cartesians(5, 3))

        for (dimensions in sizesByDimensions) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val matrix = matrix(TYPE, sizes)
                Assert.assertFalse(matrix.isScalar())
                Assert.assertTrue(matrix.isVector())
                Assert.assertFalse(matrix.isMatrix(sizes.size))
                printResult(sizes, intArrayOf(), "PASS")
            }
        }
    }

    @Test
    fun testIsMatrix() {
        val sizesByDimensions = getMatricesDims(cartesians(5, 3))

        for (dimensions in sizesByDimensions) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val matrix = matrix(TYPE, sizes)
                Assert.assertFalse(matrix.isScalar())
                Assert.assertFalse(matrix.isVector())
                Assert.assertTrue(matrix.isMatrix(sizes.size))
                printResult(sizes, intArrayOf(), "PASS")
            }
        }
    }

    @Test
    fun testTranspose() {
        val original = Matrix(Integers, 3, 3, 3, 3)
        original.set(1, 0, 0, 0, 0)
        original.set(2, 0, 1, 0, 1)
        original.set(3, 0, 2, 0, 2)
        val transposed = original.transpose()

        val allIndexesCombos = original.allIndexesCombos()

        for (indexes in allIndexesCombos) {
            val right = indexes.toIntArray()
            val inversed = indexes.inverse().toIntArray()
            val originalValue = original.getAt(*right)
            val transposedValue = transposed.getAt(*inversed)
            Assert.assertEquals(originalValue, transposedValue)
        }

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

