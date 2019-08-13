package com.home.utils.elements.type

object DoubleType: Type<Double>({ 0.0 }, Double::plus, Double::minus, Double::times)
object IntType: Type<Int>({ 0 }, Int::plus, Int::minus, Int::times)
object LongType: Type<Long>({ 0 }, Long::plus, Long::minus, Long::times)
