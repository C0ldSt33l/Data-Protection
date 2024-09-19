package encryption

import java.io.File

val FILE_PATH = arrayOf(
    arrayOf(
        """files/first key.txt""",
        """files/first line.txt""",
        """files/first encode.txt""",
        """files/first decode.txt""",
    ),
    arrayOf( 
        """files/second key.txt""",
        """files/second line.txt""",
        """files/second encode.txt""",
        """files/second decode.txt""",
    ),
)

fun first_algorithm() {
    val key = File(FILE_PATH[0][0]).readLines().first().toInt()
    println(key)
    val shifr = Scytale(key)
    val line = File(FILE_PATH[0][1]).readLines().first()
    println(line)
    val encoded_line = shifr.encode(line)
    val decoded_line = shifr.decode(encoded_line) 

    File(FILE_PATH[0][2]).writeText(encoded_line)
    File(FILE_PATH[0][3]).writeText(decoded_line)

    println(encoded_line)
    println(decoded_line)
}

fun second_algorithm() {
    val alphabet: String
    val offset: Int
    val keyword: String
    val lines = File(FILE_PATH[1][0]).readLines()
    println(lines)
    alphabet = lines[0]
    offset = lines[1].toInt()
    keyword = lines[2]

    val line = File(FILE_PATH[1][1]).readLines().first()
    println(line)

    val shifr = CezareWithKeyWord(alphabet, offset, keyword)
    val encoded_line = shifr.encode(line)
    val decoded_line = shifr.decode(encoded_line)

    File(FILE_PATH[1][2]).writeText(encoded_line)
    File(FILE_PATH[1][3]).writeText(decoded_line)
}

fun main() {
    first_algorithm()
}