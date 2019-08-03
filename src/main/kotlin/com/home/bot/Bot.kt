package com.home.bot

data class Bot(
    val buyInterval: Double,
    val sellInterval: Double): SimpleConsumer<Pair<Time, Cost>, Unit>()
{
    override val acceptBody: (Pair<Time, Cost>) -> Unit  = {
        println(""""time": ${it.first.value}; "cost": ${it.second.value}""")
    }

    val values: HashMap<Time, Cost> = HashMap()

    fun addValue(value: Pair<Time, Cost>) {
        values.put(value.first, value.second)
    }
}

class Cost(val value: Double)
class Time(val value: Long)
