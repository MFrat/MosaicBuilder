package model.helper

import model.Mosaic
import model.Tile
import java.awt.image.BufferedImage

fun Mosaic.drawTile(tile: Tile, i: Int, j: Int) {
    for (k in 0 until this.dimension) {
        for (l in 0 until this.dimension) {
            this.image.setRGB(i*this.dimension+k, j*this.dimension+l, toCode(tile.pixels[k][l]!!))
        }
    }
}

fun Mosaic.drawTiles(): BufferedImage {
    for (i in 0 until this.tiles.size) {
        for (j in 0 until this.tiles[i].size) {
            drawTile(this.tiles[i][j]!!, i, j)
        }
    }

    return this.image
}

fun Mosaic.findTilesDimen(): Int {
    return this.tiles[0][0]!!.pixels.size
}