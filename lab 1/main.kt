package encryption

fun main() {
    val shifr = Scytale(6)
    // println(shifr.encode("text has to has more words than i thought"))
    // println(shifr.encode("тот же тект что и сверху. Правда уже не тот же самый да и похуй"))
    val encoded_str = shifr.encode("ПРОИЗВЕДЕН ЗАПУСК СПУТНИКА")
    val decodeed_str = shifr.decode(encoded_str)

    println("----------")
    println(encoded_str)
    println("----------")
    println(decodeed_str)

    val arr = arrayOf(1, 2, 3)
    println(arr.contentToString())
}