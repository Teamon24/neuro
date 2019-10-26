package com.home.bot

import io.reactivex.functions.Consumer

abstract class SimpleConsumer<T, K>: Consumer<T> {
    abstract val acceptBody: (T) -> K
    override fun accept(t: T) { acceptBody(t) }
}