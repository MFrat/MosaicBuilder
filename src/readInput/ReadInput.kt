package readInput

import logger.DEBUG_LEVEL
import logger.INFO_LEVEL
import model.Pixel
import model.Tile
import model.helper.toPixel
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.system.measureTimeMillis

class ReadInput(val infoCallback: (info: String, level: String) -> Unit = { _, _ -> }) {

    fun readImage(imagePath: String): BufferedImage {
        return ImageIO.read(File(imagePath))
    }

    fun readTiles(tilesPath: String): List<Tile> {
        infoCallback("Reading tiles from $tilesPath", INFO_LEVEL)
        return getFilesList(tilesPath)
            .map(::readTile)
    }

    fun readTilesMultiThread(tilesPath: String): List<Tile> {
        val nThreads = 4
        infoCallback("Reading tiles from $tilesPath, with $nThreads thread(s)", INFO_LEVEL)
        val chunkedFiles = splitChunks(nThreads, getFilesList(tilesPath))
        val tArray = arrayOfNulls<Thread>(nThreads)
        val results = Array(nThreads) { arrayOfNulls<Tile>(chunkedFiles[0].size) }
        for (i in 0 until nThreads) {
            val t = Thread { results[i] = chunkedFiles[i].map { readTile(it) }.toTypedArray() }
            tArray[i] = t
            t.start()
        }

        for (i in tArray) {
            i?.join()
        }

        return results.flatMap { it.asList() }.filterNotNull()
    }

    private fun filterFiles(name: String): Boolean {
        return name.endsWith(".jpg")
    }

    private fun getFilesList(folder: String): List<String> {
        infoCallback("Filtering tile's files", DEBUG_LEVEL)
        return File(folder)
            .listFiles { _, name -> filterFiles(name) }
            .map { it.path }
    }

    private fun readTile(imagePath: String): Tile {
        infoCallback("Reading tile $imagePath", DEBUG_LEVEL)
        val image = readImage(imagePath)
        val finalArray = initArray(image.width, image.height)
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = toPixel(image.getRGB(i, j))
                finalArray[i][j] = pixel
            }
        }

        return Tile(finalArray)
    }

    private fun initArray(width: Int, heigth: Int): Array<Array<Pixel?>> {
        return Array(width) { arrayOfNulls<Pixel>(heigth) }
    }

    private fun splitChunks(nChunks: Int, list: List<String>): List<List<String>> {
        val size = list.size
        val chunkSize = size / nChunks

        return list.chunked(chunkSize)
    }
}

fun main() {
    val input2 = ReadInput()
    val time2 = measureTimeMillis {
//        input2.readTiles("/home/mfratane/Documents/MegaPack/pack")
        input2.readTilesMultiThread("/home/mfratane/Documents/MegaPack/pack")
    }

    print("Singlethread $time2")
}