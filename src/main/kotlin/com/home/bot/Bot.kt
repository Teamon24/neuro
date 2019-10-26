package com.home.bot

data class Bot(
    val buyInterval: Double,
    val sellInterval: Double): SimpleConsumer<Point<Long, Double>, Unit>()
{
    override val acceptBody: (Point<Long, Double>) -> Unit  = {
        println(""""time": ${it.x}; "cost": ${it.y}""")
    }

    val values: HashMap<Long, Double> = HashMap()

    fun addValue(value: Point<Long, Double>) {
        values.put(value.x, value.y)
    }
}
