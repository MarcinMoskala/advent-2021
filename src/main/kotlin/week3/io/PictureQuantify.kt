package week3.io

import week3.VectorDoubleListQuantifier
import java.awt.FlowLayout
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

fun main() {
    showImagesQuantification()
}

fun showImagesQuantification() {
    val imgData = getImageRgb("img.jpg")
    fun quantified(levels: Int): ImageData {
        val quantified = VectorDoubleListQuantifier.quantify(imgData.pixelsRgb, levels)
        return imgData.copy(pixelsRgb = quantified)
    }

    fun displayQualified(numberOfColors: Int) {
        displayImage("Qualified to $numberOfColors colors", quantified(numberOfColors))
    }
    for (i in 1..10) {
        displayQualified(i)
    }
    displayQualified(20)
    displayQualified(30)
    displayQualified(40)
    displayQualified(50)
    displayQualified(100)

    saveImage("img1.jpg", quantified(1))
    saveImage("img10.jpg", quantified(10))
    saveImage("img100.jpg", quantified(100))
    saveImage("img1000.jpg", quantified(1000))
}

private fun displayImage(title: String, imgData: ImageData) {
    val img = BufferedImage(imgData.w, imgData.h, imgData.type)
    img.setRGB(0, 0, imgData.w, imgData.h, imgData.pixelsRgb.map { it.margeRgb() }.toIntArray(), 0, imgData.w)
    JFrame().apply {
        this.title = title
        layout = FlowLayout()
        setSize(imgData.w, imgData.h)
        add(JLabel().apply { icon = ImageIcon(img) })
        isVisible = true
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }
}

private fun saveImage(name: String, imgData: ImageData) {
    val img = BufferedImage(imgData.w, imgData.h, imgData.type)
    img.setRGB(0, 0, imgData.w, imgData.h, imgData.pixelsRgb.map { it.margeRgb() }.toIntArray(), 0, imgData.w)
    ImageIO.write(img, "jpg", File(name))
}

data class ImageData(val pixelsRgb: List<List<Double>>, val w: Int, val h: Int, val type: Int)

fun getImageRgb(imgName: String): ImageData {
    val img = ImageIO.read(File(imgName))
    val w = img.width
    val h = img.height
    val type = img.type
    val rgbs = IntArray(w * h)
    img.getRGB(0, 0, w, h, rgbs, 0, w)
    return ImageData(rgbs.map { it.splitRgb() }, w, h, type)
}

fun Int.splitRgb(): List<Double> =
    listOf(this shr 24 and 0xff, this shr 16 and 0xff, this shr 8 and 0xff, this and 0xff).map { it.toDouble() }

fun List<Double>.margeRgb(): Int {
    val (a, r, g, b) = this.map { it.toInt() }
    return a shl 24 or (r shl 16) or (g shl 8) or b
}