import logger.info
import process.MosaicBuilder
import kotlin.system.measureTimeMillis

fun main() {
    val imagePack = "/home/mfratane/Documents/MegaPack/pack"
    val inputImage = "/home/mfratane/Pictures/1542849578533-1024x683.jpg"
    val outputImage = "/home/mfratane/Pictures/result.png"
    
    val mosaicBuilder = MosaicBuilder(imagePack, inputImage, outputImage, ::log)
    mosaicBuilder.execute()
}

fun log(message: String, level: String) {
    when (level) {
        "INFO" -> info(message)
    }
}