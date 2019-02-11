package logger

import java.time.LocalDateTime

const val INFO_LEVEL = "INFO"
const val DEBUG_LEVEL = "DEBUG"

fun log(type: String, message: String) {
    println("[$type] ${LocalDateTime.now()}: $message")
}

fun info(message: String) {
    log(INFO_LEVEL, message)
}

fun debug(message: String) {
    log(DEBUG_LEVEL, message)
}