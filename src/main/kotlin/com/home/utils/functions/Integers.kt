package com.home.utils.functions

import kotlin.random.Random

fun Int.randomExclusive() = Random.nextInt(this)
fun Int.more(that: Int) = this > that
fun Int.less(that: Int) = this < that
