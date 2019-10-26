package com.home.utils.elements

import com.home.utils.ArrayUtils
import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.type.Integers
import com.home.utils.operators.allIndexesCombos
import org.junit.Assert
import org.junit.Ignore
import org.junit.Test

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
            val arrayElement = ArrayUtils.get(arrayNdim, TYPE.clazz(), *indexes.toIntArray())
            val matrixElement = matrix.getAt(indexes)
            Assert.assertEquals(matrixElement, arrayElement)
        }
    }

}

private fun <T> Matrix<T>.randomInit() {
    val allIndexesCombos = this.allIndexesCombos()
    for (indexes in allIndexesCombos) {
        this.setAt(indexes, type.random())
    }
}

private fun Matrix<Int>.init() {
    var i = 0;
    val allIndexesCombos = this.allIndexesCombos()
    for (indexes in allIndexesCombos) {
        this.setAt(indexes, i++)
    }
}
