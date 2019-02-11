package model

import model.helper.calculateAverage

class Tile(val pixels: Array<Array<Pixel?>>) {

    val average by lazy {
        this.calculateAverage()
    }
}