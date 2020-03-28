package com.home.utils.elements.type

import com.home.utils.functions.randomExclusive

object Doubles: Type<Double>({ 0.0 }, Double::plus, Double::minus, Double::times) {
    override fun random() = (1..20).randomExclusive().toDouble()
    override fun clazz() = Double::class
}

object Integers: Type<Int>({ 0 }, Int::plus, Int::minus, Int::times) {
    override fun random() = (1..20).randomExclusive()
    override fun clazz() = Int::class
}

object Longs: Type<Long>({ 0L }, Long::plus, Long::minus, Long::times) {
    override fun random() = (1..20).randomExclusive().toLong()
    override fun clazz() = Long::class
}

