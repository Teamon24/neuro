package com.home.utils

object ThreadUtils {
    fun printThread() {
        println(Thread.currentThread().name.toUpperCase())
    }
}