package com.home.utils

object ArrayUtils {

    inline fun <reified T> nDimArray(sizes: IntArray, init: () -> T): Array<*> {
        val elements = (1..sizes[sizes.size - 1]).map { init() }.toTypedArray()
        var result: Array<*> = arrayOf(*elements)

        for (n in sizes.size - 1 downTo 1) {
            val amount = sizes[n-1]
            result = arr(result, amount)
        }
        return result;
    }

    fun arr(arr:Array<*>, n: Int): Array<*> {
        val result: ArrayList<Array<*>> = ArrayList()
        for (i in 1..n) {
            result.add(arr)
        }
        return result.toTypedArray()

    }
}