package com.home.neuro.ui.styles

import com.home.neuro.ui.styles.Styles.Companion.screenWidth
import javafx.scene.paint.Color
import tornadofx.*

/**
 *
 */
class TabStyles : Stylesheet() {
    companion object {
        val matrixTabPane by cssclass()
        val appTab by cssclass()
        var tabsAmaount: Int = 0
    }

    init {
        matrixTabPane {
            tabLabel {
                focusColor = Color.TRANSPARENT
            }
            tabMinWidth = screenWidth / tabsAmaount / 2
            tabMinHeight = 2.em
        }

        appTab {

            backgroundColor += Styles.APP_COLOR set 0.2.a
            and(hover) {
                borderColor += box(Styles.APP_COLOR set 0.3.a)
                backgroundColor += Styles.APP_COLOR set 0.8.a
            }

            and(focused) {

            }
        }
    }
}