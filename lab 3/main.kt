// import kotlin.test.*
package lab3

class Playflay(
    var alphabet: String,
    val row_count: Int, // 5
    val col_count: Int, // 8
    var keyword: String // ПРЕФЕКТУРА
) {
    val table = Array(row_count * col_count) { '_' }

    init {
        this.alphabet = this.alphabet.uppercase()
        this.keyword = this.keyword.uppercase()

        println("alphabet size: ${this.alphabet.length}")
        val keyword_without_duplicate = this.keyword.toCharArray().distinct().joinToString("")
        for ((i, char) in keyword_without_duplicate.withIndex()) {
            this.table[i] = char
        }
        println(keyword_without_duplicate)

        val alphabet_without_keyword = this.alphabet.filterNot { it in keyword_without_duplicate }
        println(alphabet_without_keyword)

        for ((i, char) in alphabet_without_keyword.withIndex()) {
            this.table[i + keyword_without_duplicate.length] = char
        }

        this.print_table()
    }

    fun encode(text: String): String {
        val text = text.uppercase()
        var encoded = ""
        for (bigramm in this.get_bigramms(text)) {
            val first_coords = this.get_char_coord(bigramm.first())
            val second_coords = this.get_char_coord(bigramm.last())

            if (first_coords == null || second_coords == null) continue

            if (first_coords.first == second_coords.first) {
                encoded += this.get_char_by_coords(Pair(first_coords.first, (first_coords.second + 1) % row_count))
                encoded += this.get_char_by_coords(Pair(second_coords.first, (second_coords.second + 1) % row_count))
                continue
            }
            if (first_coords.second == second_coords.second) {
                encoded += this.get_char_by_coords(Pair((first_coords.first + 1) % col_count, first_coords.second))
                encoded += this.get_char_by_coords(Pair((second_coords.first + 1) % col_count, second_coords.second))
                continue
            }

            encoded += this.get_char_by_coords(Pair(second_coords.first, first_coords.second))
            encoded += this.get_char_by_coords(Pair(first_coords.first, second_coords.second))
        }

        return encoded
    }

    fun decode(text: String): String {
        val text = text.uppercase()
        var decoded = ""
        for (bigramm in this.get_bigramms(text)) {
            val first_coords = this.get_char_coord(bigramm.first())
            val second_coords = this.get_char_coord(bigramm.last())
            if (first_coords == null || second_coords == null) continue

            if (first_coords.first == second_coords.first) {
                decoded += this.get_char_by_coords(Pair(first_coords.first, if (first_coords.second - 1 < 0) row_count - 1 else first_coords.second - 1))
                decoded += this.get_char_by_coords(Pair(second_coords.first, if (second_coords.second - 1 < 0) row_count - 1 else second_coords.second - 1))
                continue
            }
            if (first_coords.second == second_coords.second) {
                decoded += this.get_char_by_coords(Pair(if (first_coords.first - 1 < 0) col_count - 1 else first_coords.first - 1, first_coords.second))
                decoded += this.get_char_by_coords(Pair(if (second_coords.first - 1 < 0)  col_count - 1 else second_coords.first - 1, second_coords.second))
                continue
            }

            decoded += this.get_char_by_coords(Pair(second_coords.first, first_coords.second))
            decoded += this.get_char_by_coords(Pair(first_coords.first, second_coords.second))
        }

       return decoded 
    }

    private fun get_char_by_coords(coords: Pair<Int, Int>): Char {
        val i = coords.second * col_count + coords.first
        return this.table[i]
    }

    private fun get_char_coord(char: Char): Pair<Int, Int>? {
        val i = this.table.indexOf(char)
        if (i < 0) return null
        return Pair(i % col_count, i / col_count)
    }

    private fun get_bigramms(text: String): MutableList<String> {
        val bigramms = mutableListOf<String>()
        var i = 0
        while (i < text.length) {
            var bigramm = if (i == text.length - 1) text.slice(i..i) else text.slice(i..i+1)
            if (bigramm.length == 2 && bigramm.first() == bigramm.last()) {
                bigramm = bigramm.first() + "-"
                bigramms.add(bigramm)
                
                i += 1
                continue
            }

            bigramms.add(bigramm)
            i += 2
        }
        val last = bigramms.lastIndex
        if (bigramms[last].length == 1) {
            bigramms[last] += " "
        }

        return bigramms
    }

    private fun print_table() {
        for ((i, char) in this.table.withIndex()) {
            if (i % col_count == 0) println()
            print("$char ")
        }
        println("\n-------------------\n")
    }
}


fun main() {
    // val shifr = Playflay(
    //     "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,-:!?",
    //     5, 8,
    //     "ПРЕФЕКТУРА"
    // )

    // val text = "ВО ВРЕМЯ ПЕРВОЙ МИРОВОЙ ВОЙНЫ ИСПОЛЬЗОВАЛИСЬ БИГРАММНЫЕ ШИФРЫ"
    // println(text)
    // val encoded = shifr.encode(text)
    // println(encoded)
    // val decoded = shifr.decode(encoded)
    // println(decoded)

    // println("ara".get_bigramms().joinToString(","))
    Test().run()
}