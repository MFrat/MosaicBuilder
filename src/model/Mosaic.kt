package model

import model.helper.drawTiles
import model.helper.findTilesDimen
import java.awt.image.BufferedImage

class Mosaic(private val inputImageWidth: Int, private val inputImageHeight: Int, val tiles: Array<Array<Tile?>>) {
    val image by lazy { createImage() }
    val dimension by lazy { findTilesDimen() }

    fun draw(): BufferedImage {
        return drawTiles()
    }

    private fun createImage(): BufferedImage {
        return BufferedImage(dimension*inputImageWidth, dimension*inputImageHeight, BufferedImage.TYPE_INT_RGB)
    }
}