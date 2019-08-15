package com.home.utils

import kotlin.reflect.KProperty

class Property<R, PropType : Any>(
    private val name: String = "",
    val initializer: (R) -> PropType = {
        throw IllegalStateException("Property ${if (name.isBlank()) "" else "'$name' "}not initialized.")
    })
{
    private var property: PropType? = null

    operator fun getValue(thisRef: R, property: KProperty<*>): PropType =
        this.property ?: setValue(thisRef, property, this.initializer(thisRef))

    operator fun setValue(thisRef: R, property: KProperty<*>, value: PropType): PropType {
        this.property = value
        return value
    }
}