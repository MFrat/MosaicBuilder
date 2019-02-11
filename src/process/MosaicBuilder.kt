package process

import logger.INFO_LEVEL
import process.helper.buildMosaicMultiThread
import process.helper.writeImage
import readInput.ReadInput
import kotlin.system.measureTimeMillis


class MosaicBuilder(
    private val tilesPath: String, private val inputImagePath: String,
    private val outputImagePath: String, val infoCallback: (message: String, level: String) -> Unit = { _, _ -> }
) {
    private val input = ReadInput(infoCallback)

    private val tiles by lazy { input.readTilesMultiThread(tilesPath) }
    val inputImage by lazy { input.readImage(inputImagePath) }
    private val mosaic by lazy { buildMosaicMultiThread(this.tiles) }

    fun execute() {
        val time = measureTimeMillis { writeImage(mosaic.draw(), outputImagePath) }
        infoCallback("Process finished in ${time}ms", INFO_LEVEL)
    }
}