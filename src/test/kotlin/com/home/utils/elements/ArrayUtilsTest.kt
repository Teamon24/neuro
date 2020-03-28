package com.home.utils.elements

import com.home.utils.ArrayUtils
import com.home.utils.elements.latest.MatrixND
import com.home.utils.elements.type.Integers
import com.home.utils.operators.allIndexesCombos
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test
import kotlin.random.Random

class ArrayUtilsTest {

    @Ignore("Test is not ready yet.")
    @Test fun testArrayCreation() {
        val size = 7
        val dimension = 3

        println((1L..dimension).fold(1) { acc: Long, _ -> acc*size })

        val sizes = size.toArray(dimension)
        val matrix = MatrixUtils.matrix(Integers, sizes)
        matrix.init()
        val arrayNdim = ArrayUtils.nDimArray(matrix)
        val allIndexesCombos = matrix.allIndexesCombos()
        for (indexes in allIndexesCombos) {
            val arrayElement = ArrayUtils.get(arrayNdim, indexes.toIntArray())
            val matrixElement = matrix.getAt(indexes)
            Assert.assertEquals(matrixElement, arrayElement)
        }
    }

    @Test fun testGetSizes() {
        val expected = intArrayOf(2, 3, 4, 5, 6)
        val array = ArrayUtils.nDimArray(expected) { 0 }
        val actual = ArrayUtils.getSizes(array)
        Assert.assertArrayEquals(expected, actual)
    }

    @Ignore("TEST HAS NOT BEEN FINISHED YET")
    @Test fun testPrintln() {
        val expected = intArrayOf(3, 3, 3)
        val random = Random(12)
        val array = ArrayUtils.nDimArray(expected) { random.nextInt() }
        ArrayUtils.println(array)
    }

}

private fun <T> MatrixND<T>.randomInit() {
    val allIndexesCombos = this.allIndexesCombos()
    for (indexes in allIndexesCombos) {
        this.setAt(indexes, type.random())
    }
}

private fun MatrixND<Int>.init() {
    var i = 0;
    val allIndexesCombos = this.allIndexesCombos()
    for (indexes in allIndexesCombos) {
        this.setAt(indexes, i++)
    }
}
