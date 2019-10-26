import com.home.utils.Timer
import com.home.utils.functions.inverse
import org.junit.Test

/**
 *
 */
class ShemaGornera {
    @Test
    fun a() {
        val base = 2
        val a = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9).toIntArray()
        println(Timer.nanoCount { gorner(a, base) }.second)
        println(Timer.nanoCount { common(a.inverse(), base) }.second)
    }

    private fun gorner(a: IntArray, base: Int): Int {
        return a.reduce { acc, digit -> acc * base + digit }
    }

    private fun common (a: IntArray, base: Int): Int {
        var number = 0
        for (i in 0 until a.size) {
            number += a[i] * base.pow(i)
        }
        return number
    }
}

private fun Int.pow(i: Int): Int {
    var result = this
    if (i == 0) return 1
    if (i == 1) return this

    repeat(i - 1) {
        result *= this
    }

    return result;
}
