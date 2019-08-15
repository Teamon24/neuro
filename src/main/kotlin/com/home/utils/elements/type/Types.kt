package com.home.utils.elements.type

import kotlin.reflect.KClass

object DoubleType: Type<Double>({ 0.0 }, Double::plus, Double::minus, Double::times) {
    override fun clazz() = Double::class as KClass<Double>
}

object IntType: Type<Int>({ 0 }, Int::plus, Int::minus, Int::times) {
    override fun clazz() = Int::class as KClass<Int>
}

object LongType: Type<Long>({ 0 }, Long::plus, Long::minus, Long::times) {
    override fun clazz() = Long::class as KClass<Long>
}

