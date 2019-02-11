package process.helper

import logger.INFO_LEVEL
import model.Mosaic
import model.Pixel
import model.Tile
import model.helper.getSimilarity
import model.helper.toPixel
import process.MosaicBuilder
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun findBest(pixel: Pixel, tiles: List<Tile>): Tile {
    if (tiles.size == 1) return tiles[0]

    var currentBest = tiles[0]
    var currentDiff = pixel.getSimilarity(currentBest.average)
    for (i in 1 until tiles.size) {
        val diff = pixel.getSimilarity(tiles[i].average)
        if (diff < currentDiff) {
            currentDiff = diff
            currentBest = tiles[i]
        }
    }

    return currentBest
}

fun MosaicBuilder.buildMosaic(tiles: List<Tile>): Mosaic {
    this.infoCallback("Selecting best tiles for the input image", INFO_LEVEL)

    val width = this.inputImage.width
    val height = this.inputImage.height

    val finalMatrix = Array(width) { arrayOfNulls<Tile>(height) }

    for (i in 0 until width) {
        for (j in 0 until height) {
            val pixelCode = inputImage.getRGB(i, j)
            val best = findBest(toPixel(pixelCode), tiles)
            finalMatrix[i][j] = best
        }
    }

    return Mosaic(width, height, finalMatrix)
}

fun MosaicBuilder.buildMosaicMultiThread(tiles: List<Tile>): Mosaic {
    this.infoCallback("Selecting best tiles for the input image, with 4 thread(s)", INFO_LEVEL)

    val width = this.inputImage.width
    val height = this.inputImage.height

    val finalMatrix = Array(width) { arrayOfNulls<Tile>(height) }

    val t1 = Thread { this.buildMosaic(tiles, finalMatrix, 0, width/2, 0, height/2) }
    val t2 = Thread { this.buildMosaic(tiles, finalMatrix, width/2, width, 0, height/2) }
    val t3 = Thread { this.buildMosaic(tiles, finalMatrix, 0, width/2, height/2, height) }
    val t4 = Thread { this.buildMosaic(tiles, finalMatrix, width/2, width, height/2, height) }

    t1.start();t2.start();t3.start();t4.start()
    t1.join();t2.join();t3.join();t4.join()

    return Mosaic(width, height, finalMatrix)
}

fun MosaicBuilder.buildMosaic(tiles: List<Tile>, finalMatrix: Array<Array<Tile?>>, startWidth: Int, endWidth: Int, startHeight: Int, endHeight: Int) {
    for (i in startWidth until endWidth) {
        for (j in startHeight until endHeight) {
            val pixelCode = inputImage.getRGB(i, j)
            val best = findBest(toPixel(pixelCode), tiles)
            finalMatrix[i][j] = best
        }
    }
}

fun MosaicBuilder.writeImage(image: BufferedImage, path: String) {
    this.infoCallback("Writing final image: $path", INFO_LEVEL)
    ImageIO.write(image, "png", File(path))
}