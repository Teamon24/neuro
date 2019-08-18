package com.home.utils.elements.type

import kotlin.reflect.KClass

object Doubles: Type<Double>({ 0.0 }, Double::plus, Double::minus, Double::times) {
    override fun clazz() = Doubles::class as KClass<Double>
}

object Integers: Type<Int>({ 0 }, Int::plus, Int::minus, Int::times) {
    override fun clazz() = Int::class as KClass<Int>
}

object Longs: Type<Long>({ 0 }, Long::plus, Long::minus, Long::times) {
    override fun clazz() = Longs::class as KClass<Long>
}

