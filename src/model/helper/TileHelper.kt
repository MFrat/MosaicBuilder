package model.helper

import model.Pixel
import model.Tile



fun Tile.calculateAverage(): Pixel {
    var red = 0
    var blue = 0
    var green = 0

    val total = this.pixels.size * this.pixels.size

    for (i in 0 until this.pixels.size) {
        for (j in 0 until this.pixels[i].size) {
            red += this.pixels[i][j]!!.red
            blue += this.pixels[i][j]!!.blue
            green += this.pixels[i][j]!!.green
        }
    }

    red /= total
    blue /= total
    green /= total

    return Pixel(red, green, blue)
}