package com.home.utils.elements

import com.home.utils.ArrayUtils
import com.home.utils.Timer
import com.home.utils.elements.MatrixUtils.dimensionSizesList
import com.home.utils.elements.MatrixUtils.get3DMatricesDims
import com.home.utils.elements.MatrixUtils.getMatricesDims
import com.home.utils.elements.MatrixUtils.getVectorsDims
import com.home.utils.elements.MatrixUtils.matrix
import com.home.utils.elements.MatrixUtils.matrix3D
import com.home.utils.elements.latest.*
import com.home.utils.elements.type.Integers
import com.home.utils.functions.inverse
import com.home.utils.functions.invoke
import com.home.utils.functions.randomExclusive
import com.home.utils.operators.allIndexesCombos
import org.junit.Assert
import org.junit.Test
import java.util.concurrent.atomic.AtomicReference
import kotlin.random.Random

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
        val cartesians = dimensionSizesList(3, 15)

        for (dimensions in cartesians) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()

                val expectedMatrix = ArrayUtils.nDimArray(sizes) { 0 }
                val actualMatrix = matrix(TYPE, sizes)

                val indexes = this.randomIndexesFrom(sizes)
                this.set(expectedMatrix, 1, *indexes)
                actualMatrix.set(1, *indexes)

                val expectedValue = ArrayUtils.get(expectedMatrix, TYPE.clazz(), *indexes)
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
        val dimensionsList = get3DMatricesDims(dimensionSizesList(3, 15))
        for (dimensions in dimensionsList) {
            for (dimension in dimensions) {
                val sizes = dimension.toIntArray()
                val indexes = this.randomIndexesFrom(sizes)
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
     * and checks cashing mechanism of internal matrix elements.
     */
    @Test
    fun testCash() {
        val dimensionSizesList = dimensionSizesList(3, 15)
        val vectorDimensions = getVectorsDims(dimensionSizesList)
        val matricesDimensions = getMatricesDims(dimensionSizesList)
        for (dimensionsSizes in vectorDimensions + matricesDimensions) {
            for (sizes in dimensionsSizes) {
                val sizesArr = sizes.toIntArray()
                val matrix = matrix(TYPE, sizesArr)
                val firstSize = sizesArr[0]
                val randomExclusive = firstSize.randomExclusive()
                val matrix1 = matrix[randomExclusive]
                val matrix2 = matrix[randomExclusive]
                Assert.assertTrue(matrix1 === matrix2)
                println("$matrix1 == $matrix2")
            }
        }
    }

    @Test
    fun testIsScalar() {
        val cartesians = dimensionSizesList(7, 1)
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
        val sizesByDimensions = getVectorsDims(dimensionSizesList(5, 3))

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
        val sizesByDimensions = getMatricesDims(dimensionSizesList(5, 3))

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


    @Test
    fun testGet() {
        val size = 7
        val dimension = 3
        println((1L..dimension).fold(1) { acc: Long, _ -> acc*size })
        val sizes = size.toArray(dimension)
        val matrix = matrix(Integers, sizes)
        val arrayNdim = ArrayUtils.nDimArray(sizes) { 0 }

        val allIndexesCombos = matrix.allIndexesCombos()

        val times = 10000

        println(
            (1..times).map {
                Timer.nanoCount("matrix", toLog = false) {
                    allIndexesCombos.forEach { matrix.getAt(it) }
                }
            }
            .map { it.second }
            .sum() / times
        )

        println()

        println(
            (1..times).map {
                Timer.nanoCount("array", toLog = false) {
                    allIndexesCombos.forEach { ArrayUtils.get(arrayNdim, TYPE.clazz(), it, sizes) }
                }
            }
            .map { it.second }
            .sum() / times
        )
    }

    @Test
    fun testGet2() {
        val size = 7
        val dimension = 3
        val sizes = size.toArray(dimension)
        val matrix = matrix(Integers, sizes)
        val arrayNdim = ArrayUtils.nDimArray(matrix)

        val randomIndexes = arrayListOf<Int>()
        (0 until dimension) {
            randomIndexes.add(size.randomExclusive())
        }

        println(
            Timer.nanoCount("matrix") {
                randomIndexes.forEach {
                    var any: Any = matrix[it]
                    if (any is Matrix<*>) {
                        any = any[it]
                    }
                }
            }.second
        )


        println(
            Timer.nanoCount("array") {
                var array: Array<*> = arrayNdim
                randomIndexes.forEach {
                    array = array(arrayNdim)[it]
                }
            }.second
        )

    }

    private fun array(any: Any?):Array<Array<*>> = any as Array<Array<*>>

    private fun randomIndexesFrom(sizes: IntArray): IntArray {
        val random = Random(seed = 245813549731)
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

fun Int.toArray(dimension: Int): IntArray {
    val arrayListOf = arrayListOf<Int>()
    repeat(dimension) {
        arrayListOf.add(this)
    }
    return arrayListOf.toIntArray()
}

