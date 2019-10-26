package com.home.neuro.ui.view

import com.home.neuro.NeuronNet
import com.home.neuro.ui.styles.Styles
import com.home.utils.CollectingUtils
import com.home.utils.ImageUtils
import com.home.utils.css.CSSUtils
import tornadofx.*
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    launch<NeuroApp>(args)
}

class NeuroApp: App(AppView::class)

val dataSetUIImageSize = 50

class AppView: View("AppView") {
    override val root = scrollpane {
        importStylesheet(Styles::class)
        CSSUtils.addCss(this, "css/scroll-bar.css")
        val dataSetImageSize = 30
        Styles.dataSetUIImageSize = dataSetUIImageSize.px
        val neuronNet = NeuronNet(dataSetImageSize * dataSetImageSize, 3, 10)
        this.add(MainView(neuronNet, createDataSet(dataSetImageSize)))
    }

    private fun createDataSet(imageSize: Int): Map<Int, MutableList<BufferedImage>> {
        val path = "/images/digits"

        val digitsPath = javaClass.getResource("$path").toURI().path.drop(1)
        val pictures = this.pictures(digitsPath)
        val picturesToDigits = pictures.drop(1).map {
            val fileName = it.fileName
            val image = ImageIO.read(File("$digitsPath/$fileName"))
            ImageUtils.resize(image, imageSize, imageSize) to "${fileName.toString()[0]}".toInt()
        }.toMap()

        return CollectingUtils.flip(picturesToDigits)
    }


    private fun pictures(digitsPath: String): MutableList<Path> {
        try {
            val digitsPictures = Paths.get("$digitsPath")
            return Files.walk(digitsPictures).collect(Collectors.toList())
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }
}