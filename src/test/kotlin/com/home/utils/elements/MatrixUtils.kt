package com.home.utils.elements

import com.google.common.collect.Lists
import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.latest.Matrix3D
import com.home.utils.elements.type.Type
import com.home.utils.functions.*

object MatrixUtils {

    /**
     *
     * @param dimension amount of matrix sizes.
     * @param size maximal length of each size in dimension.
     * @return all possibles combinations of sizes with dimensions
     *
     * For example, for dimension = 3 and size = 4 will be generated:
     * all possible combinations of sizes {1,2,3,4} by 1 dimension
     *
     * 1; 2; 3; 4
     * 4^1 = 4
     *
     * all possible combinations of sizes {1,2,3,4} by 2 dimension
     *
     * 1-1; 1-2; 1-3; 1-4
     * 2-1; 2-2; 2-3; 2-4
     * 3-1; 3-2; 3-3; 3-4
     *
     * = 4^2 = 16
     *
     * all possible combinations of sizes {1,2,3,4} by 3 dimension
     *
     * 1-1-1; 1-1-2; 1-1-3; 1-1-4;
     * 1-2-1; 1-2-2; 1-2-3; 1-2-4;
     * 1-3-1; 1-3-2; 1-3-3; 1-3-4;
     * 1-4-1; 1-4-2; 1-4-3; 1-4-4;
     *
     * 2-1-1; 2-1-2; 2-1-3; 2-1-4;
     * 2-2-1; 2-2-2; 2-2-3; 2-2-4;
     * 2-3-1; 2-3-2; 2-3-3; 2-3-4;
     * 2-4-1; 2-4-2; 2-4-3; 2-4-4;
     *
     * 3-1-1; 3-1-2; 3-1-3; 3-1-4;
     * 3-2-1; 3-2-2; 3-2-3; 3-2-4;
     * 3-3-1; 3-3-2; 3-3-3; 3-3-4;
     * 3-4-1; 3-4-2; 3-4-3; 3-4-4;
     *
     * 4-1-1; 4-1-2; 4-1-3; 4-1-4;
     * 4-2-1; 4-2-2; 4-2-3; 4-2-4;
     * 4-3-1; 4-3-2; 4-3-3; 4-3-4;
     * 4-4-1; 4-4-2; 4-4-3; 4-4-4;
     *
     * = 4^3 = 64
     *
     */
    fun dimensionSizesList(dimension: Int, size: Int): List<MutableList<List<Int>>> {
        val forContesianProduct = arrayListOf<List<List<Int>>>()
        for(i in 1..dimension) {
            val cartesianElements = arrayListOf<List<Int>>()
            (1..i) {
                cartesianElements.add((1..size).toList())
            }
            forContesianProduct.add(cartesianElements)
        }
        val dimensionSizesList = arrayListOf<MutableList<List<Int>>>()

        for (ds in forContesianProduct) {
            val dimensionSizes = Lists.cartesianProduct(ds)
            dimensionSizesList.add(dimensionSizes)
        }

        return dimensionSizesList
    }

    fun <T> matrix  (type: Type<T>, sizes: IntArray) = Matrix(type, *sizes)
    fun <T> matrix2D(type: Type<T>, sizes: IntArray) = Matrix2D(type, sizes[0], sizes[1])
    fun <T> matrix3D(type: Type<T>, sizes: IntArray) = Matrix3D(type, sizes[0], sizes[1], sizes[2])

    fun getVectorsDims(dimensionSizesList: List<MutableList<List<Int>>>) = get(dimensionSizesList) { sizes -> sizes.size > 1 && sizes.only(1) {it > 1}}
    fun get3DMatricesDims(dimensionSizesList: List<MutableList<List<Int>>>) = get(dimensionSizesList) { sizes -> sizes.size == 3 && sizes.min(3) {it > 1}}
    fun getMatricesDims(dimensionSizesList: List<MutableList<List<Int>>>) = get(dimensionSizesList) { sizes -> sizes.size > 1 && sizes.min(2) {it > 1}}

    private fun get(dimensionSizesList: List<MutableList<List<Int>>>,
                    predicate: (List<Int>) -> Boolean): ArrayList<MutableList<List<Int>>>
    {
        val satisfiedDimensionSizesList = arrayListOf<MutableList<List<Int>>>()
        for (dimensionSizes in dimensionSizesList) {
            val satisfiedDimensionSizes = arrayListOf<List<Int>>()
            for (sizes in dimensionSizes) {
                if (predicate(sizes)) {
                    satisfiedDimensionSizes.add(sizes)
                }
            }
            satisfiedDimensionSizes.isNotEmpty().ifTrue { satisfiedDimensionSizesList.add(satisfiedDimensionSizes) }
        }
        return satisfiedDimensionSizesList
    }
}