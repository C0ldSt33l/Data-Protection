import java.util.Calendar
import java.nio.ByteBuffer
import java.nio.charset.Charset

fun Long.toBytes(): ByteArray {
    return ByteArray(Long.SIZE_BYTES) {
        (this shr it * 8).toByte()
    }
}

fun ByteArray.toLong(): Long {
    require(this.size == 8) { "Byte array must be of size 8 but size: ${this.size}" }
    var number = 0L
    for ((i, byte) in this.withIndex()) {
        number = number or (byte.toLong() shl (i * 8))
    }
    return number
}

class ANSI_X9_17(vi: Long) {
    private var vi = vi

    fun nextRandomNumber(): Long {
        val date = Calendar.getInstance().timeInMillis

        val firstEDE = date.permutation()
        val secondEDE = (this.vi xor firstEDE).permutation()
        val thiredEDE = (firstEDE xor secondEDE).permutation()

        this.vi = thiredEDE

        return secondEDE
    }

    private fun Long.permutation(): Long {
       val ipTable = intArrayOf(
           58, 50, 42, 34, 26, 18, 10,  2,
           60, 52, 44, 36, 28, 20, 12,  4,
           62, 54, 46, 38, 30, 22, 14,  6,
           64, 56, 48, 40, 32, 24, 16,  8,
           57, 49, 41, 33, 25, 17,  9,  1,
           59, 51, 43, 35, 27, 19, 11,  3,
           61, 53, 45, 37, 29, 21, 13,  5,
           63, 55, 47, 39, 31, 23, 15,  7
       )
        
        val data = this.toBytes() 
        val permutedData = ByteArray(8)
        for (i in ipTable.indices) {
            val bitIndex = ipTable[i] - 1
            val byteIndex = bitIndex / 8
            val bitPosition = bitIndex % 8

            if (((data[byteIndex].toLong() shr (7 - bitPosition)).toLong() and 0x01L) != 0L) {
                permutedData[i / 8] = (permutedData[i / 8].toLong() or (0x80L shr (i % 8))).toByte() }
        }

        return permutedData.toLong()
    }

    fun setSeed(seed: Long) { this.vi = seed }
}

class Gamma(val seed: Long) {
    val rng = ANSI_X9_17(seed)
    var gammaBlocks: Array<Long> = arrayOf()

    fun encode(text: String): String {
        this.rng.setSeed(this.seed)
        val text = text + "?".repeat(if (text.length % 8 == 0) 0 else 8 - text.length % 8)
        val blockCount = text.length / Long.SIZE_BYTES
        val textBlocks = Array<Long>(blockCount) {
            val i = it * 8
            val bytes = text.slice(it..it + 7).toByteArray()
            println(bytes.size)
            bytes.toLong()
        }
        this.gammaBlocks = Array<Long>(blockCount) {
            rng.nextRandomNumber()
        }
        println("in encode")
        println(this.gammaBlocks?.joinToString("-"))

        var encoded = ""
        for (i in 0..<blockCount) {
            (this.gammaBlocks[i] xor textBlocks[i]).toBytes().forEach { encoded += it.toInt().toChar() }
        }

        return encoded
    }

    fun decode(text: String): String {
        this.rng.setSeed(seed)
        val blockCount = text.length / Long.SIZE_BYTES
        val textBlocks = Array<Long>(blockCount) {
            val i = it * 8
            text.slice(it..it + 7).toByteArray().toLong()
        }
        println("in decode")
        println(gammaBlocks.joinToString("-"))

        var decoded = ""
        for (i in 0..<blockCount) {
            (this.gammaBlocks[i] xor textBlocks[i]).toBytes().forEach { decoded += it.toInt().toChar() }
        }

        return decoded
    }
}

fun main(args: Array<String>) {
    val text = "hello world!rtym"
    val bytes = text.toByteArray()
    val shifr = Gamma(0)
    println(text)
    println(shifr.decode(shifr.encode(text)))
}