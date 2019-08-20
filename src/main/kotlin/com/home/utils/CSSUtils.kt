package com.home.utils

import javafx.scene.Parent

object CSSUtils {
    fun addCss(parent: Parent, cssResource: String)
    {
        val stockLineChartCss = javaClass.classLoader.getResource(cssResource).toExternalForm()
        parent.stylesheets.add(stockLineChartCss)
    }
}