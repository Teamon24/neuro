package com.home.utils.elements

import com.google.common.collect.Lists
import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.latest.Matrix2D
import com.home.utils.elements.latest.Matrix3D
import com.home.utils.elements.type.Type
import com.home.utils.functions.*

object MatrixTestUtils {

    /**
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
     * @param dimension amount of matrix sizes.
     * @param size maximal length of each size in dimension.
     * @return all possibles combinations of sizes with dimensions
     *
     */
    fun cartesians(dimension: Int, size: Int): List<MutableList<List<Int>>> {
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



    fun <T> matrix  (type: Type<T>, sizes: IntArray) = Matrix(type, *sizes)
    fun <T> matrix2D(type: Type<T>, sizes: IntArray) = Matrix2D(type, sizes[0], sizes[1])
    fun <T> matrix3D(type: Type<T>, sizes: IntArray) = Matrix3D(type, sizes[0], sizes[1], sizes[2])

    fun getVectorsDims(cartesians: List<MutableList<List<Int>>>): ArrayList<MutableList<List<Int>>> {
        val nonOneCartesians = get(cartesians) { sizes -> sizes.size > 1 && sizes.only(1) { it > 1 }}
        return nonOneCartesians
    }

    fun getMatricesDims(cartesians: List<MutableList<List<Int>>>): ArrayList<MutableList<List<Int>>> {
        val nonOneCartesians = get(cartesians) { sizes -> sizes.size > 1 && sizes.min(2) {it > 1}}
        return nonOneCartesians
    }

    fun get3DMatricesDims(cartesians: List<MutableList<List<Int>>>): ArrayList<MutableList<List<Int>>> {
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
}