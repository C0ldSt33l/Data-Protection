package encryption

import kotlin.math.abs

fun main() {
    val shifr = CezareWithKeyWord("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ", 3, "ШИФРОВКА")
    val encoded_line = shifr.encode("НЕПТУН")
    val decoded_line = shifr.decode(encoded_line)
    println(encoded_line)
    println(decoded_line)
}