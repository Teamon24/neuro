package com.home.utils.functions

import kotlin.random.Random
import kotlin.reflect.KProperty

class Property<PropSource, PropType : Any>
constructor(
    private val name: String = "",
    val initializer: (PropSource) -> PropType = {
        throw IllegalStateException("Property ${if (name.isBlank()) "" else "'$name' "} not initialized.")
    })
{
    private var property: PropType? = null

    operator fun getValue(thisRef: PropSource, property: KProperty<*>): PropType =
        this.property ?: setValue(thisRef, property, this.initializer(thisRef))

    operator fun setValue(thisRef: PropSource, property: KProperty<*>, value: PropType): PropType {
        this.property = value
        return value
    }
}

class RandomProp<PropSource> (
    private val name: String = "",
    val initializer: (PropSource) -> Random = {
        throw IllegalStateException("Property ${if (name.isBlank()) "" else "'$name' "} not initialized.")
    })
{
    private var random: Random? = null

    operator fun getValue(thisRef: PropSource, property: KProperty<*>): Random =
        this.random ?: setValue(thisRef, property, this.initializer(thisRef))

    operator fun setValue(thisRef: PropSource, property: KProperty<*>, value: Random): Random {
        this.random = value
        return value
    }
}