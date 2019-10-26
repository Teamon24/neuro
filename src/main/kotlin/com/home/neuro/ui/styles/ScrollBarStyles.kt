package com.home.neuro.ui.styles

import com.home.neuro.ui.styles.Styles.Companion.APP_COLOR
import tornadofx.*

/**
 *
 */
class ScrollBarStyles: Stylesheet() {
    companion object {
        val appScroll by cssclass()
    }

    init {
        scrollBar {
            incrementButton {
                borderWidth += box(15.em)
                padding = box(0.0.px)
            }

            decrementButton {
                borderWidth += box(15.em)
            }

            thumb {
                backgroundColor += APP_COLOR
                backgroundRadius += box(0.px)
                borderWidth += box(15.em)
                backgroundInsets += box(0.px)
            }

            track {
                backgroundColor += APP_COLOR - 1.a
                backgroundRadius += box(0.em)
                borderColor += box(c(0.0, 0.0, 0.0, 0.0))
                borderWidth += box(15.em)
                padding = box(20.px)
            }
        }
    }
}