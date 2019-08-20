package com.home.utils.functions

import kotlin.reflect.KProperty

class Property<PropSource, PropType : Any> (
    private val name: String = "",
    val initializer: (PropSource) -> PropType = {
        throw IllegalStateException("Property ${if (name.isBlank()) "" else "'$name' "}not initialized.")
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