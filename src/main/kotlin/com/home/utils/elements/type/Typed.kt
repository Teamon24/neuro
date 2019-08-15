package com.home.utils.elements.type

abstract class Typed<T>(val type: Type<T>) {

    operator fun T.minus(t: T): T = type.minus(this, t)
    operator fun T.plus(t: T): T = type.plus(this, t)
    operator fun T.times(t: T): T = type.times(this, t)


}
