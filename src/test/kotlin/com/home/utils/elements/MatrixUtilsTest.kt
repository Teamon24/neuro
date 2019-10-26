package com.home.utils.elements

import com.google.common.collect.Lists
import com.home.utils.functions.i
import com.home.utils.functions.invoke
import com.home.utils.functions.times
import org.junit.Assert
import org.junit.Test
import kotlin.math.pow

class MatrixUtilsTest {

    @Test
    fun testCartesians() {
        val dimension = 3
        val size = 4

        val actuals = MatrixUtils.dimensionSizesList(dimension, size)
        val expecteds = arrayListOf<List<List<Int>>>()
        val sizes = arrayListOf<Int>()

        for(i in 1..size) { sizes.add(i) }

        (1..dimension) {
            expecteds.add(Lists.cartesianProduct(sizes.times(i)))
        }

        this.assertAmounts(size, actuals)
        this.assertCombinations(expecteds, actuals)
    }

    private fun assertCombinations(expecteds: ArrayList<List<List<Int>>>,
                                   actuals: List<MutableList<List<Int>>>)
    {
        val expectedsSize = expecteds.size
        Assert.assertEquals(expectedsSize, actuals.size)
        for(i in 0 until expectedsSize) {
            val expected = expecteds[i]
            val actual = actuals[i]
            Assert.assertEquals(expected.size, actual.size)
            for (j in 0 until expected.size) {
                Assert.assertEquals(expected[j], actual[j])
            }
        }
    }

    private fun pow(i: Int, pow: Int) = i.toDouble().pow(pow).toLong().toInt()

    private fun assertAmounts(size: Int, cartesians: List<MutableList<List<Int>>>)
    {
        val expectedCount = (1..3).fold(0) { acc: Int, i: Int -> acc + pow(size, i) }

        var actualCount = 0;
        for(i in 0 until cartesians.size) { (1..cartesians[i].size) { actualCount++ } }
        Assert.assertEquals(expectedCount, actualCount)
    }
}