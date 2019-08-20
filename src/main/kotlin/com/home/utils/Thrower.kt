package com.home.utils

import com.home.utils.elements.latest.Matrix
import com.home.utils.elements.latest.MatrixType
import com.home.utils.elements.type.Typed
import kotlin.reflect.KClass

object Thrower {

    fun throwIf(matrix: Matrix<*>) = TypeStep(matrix)

    fun <T : Typed<*>> throwObjectIsNot(kClass: KClass<T>, index: Int): String =
        throwRex("""Object that is under index "$index" is not a $kClass """)

    fun throwIfWrongSize(expectedDimension:Int, matrix: Matrix<*>) {
        val actualDimension = matrix.sizes.size
        if (expectedDimension != actualDimension) {
            throwRex("""Matrix should has dimension = "$expectedDimension". Matrix has - $actualDimension """)
        }
    }

    fun throwIfOverBound(index: Int, bound: Int) {
        if (index > bound)
            throwRex("Index '$index' is out of bound '$bound'")
    }

    fun throwIfNegative(index: Int) {
        if (index < 0) throwRex("Index is negative: '$index'.")
    }

    fun throwIfBelowBound(bound: Int, index: Int) {
        if (index < bound) throwRex("Index '$index' is below bound '$bound'.")
    }

    fun throwIfWrongDimension(indexes: IntArray, sizes: IntArray) {
        val size = indexes.size
        val D = sizes.size
        if (size != D) throwRex("Indexes amount '$size' is not equal to matrix dimension: '$D'.")
    }

    fun throwIfAnyNegative(indexes: IntArray) {
        indexes.find { it < 0 }?.let {
            val negativeIndexes = indexes.withIndex().filter { it.value < 0 }
            val negatives = negativeIndexes.joinToString(truncated = "", transform = { "'${it.index}': ${it.value}" })
            throwRex("Next indexes are negatives: $negatives.")
        }
    }

    fun throwIfSizeIsLessThanAmount(size: Int, amount: Int) {
        if (size < amount) {
            throw RuntimeException("Array has size = '$size' less than passed amount = '$amount' ")
        }
    }

    fun throwIfEmpty(sizes: IntArray) {
        if (sizes.isEmpty()) {
            throw RuntimeException("Sizes is empty.")
        }
    }

    fun throwIfScalar(index: Int, size: Int) {
        if (size == 1) {
            throw java.lang.RuntimeException("Element under index = '$index' is scalar. There should be vector to return scalar ")
        }
    }

    private fun throwRex(message: String): Nothing = throw RuntimeException(message)

}

data class TypeStep(val matrix: Matrix<*>) {
    fun isNot(type: MatrixType) {
        val typeIsNotCorrect = !type.isCorrect(matrix)
        if (typeIsNotCorrect) {
            throw RuntimeException("This is not a '$type'.")
        }
    }

    fun iz(type: MatrixType) {
        val typeIsCorrect = type.isCorrect(matrix)
        if (typeIsCorrect) {
            throw RuntimeException("Type should not be '$type'.")
        }
    }
}
