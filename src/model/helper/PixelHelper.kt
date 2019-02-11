package model.helper

import model.Pixel
import java.awt.Color

fun toPixel(pixelCode: Int): Pixel {
    val color = Color(pixelCode)

    val red = color.red
    val green = color.green
    val blue = color.blue

    return Pixel(red, green, blue)
}

fun toCode(red: Int, green: Int, blue: Int): Int {
    val color = Color(red, green, blue)
    return color.rgb
}

fun toCode(pixel: Pixel): Int {
    return toCode(pixel.red, pixel.green, pixel.blue)
}

fun Pixel.getSimilarity(pixel: Pixel): Double {
    val deltaR = Math.pow((this.red - pixel.red).toDouble(), 2.0)
    val deltaG = Math.pow((this.green - pixel.green).toDouble(), 2.0)
    val deltaB = Math.pow((this.blue - pixel.blue).toDouble(), 2.0)

    return Math.sqrt((deltaR * 2 + deltaG * 4 + deltaB * 3))
}

