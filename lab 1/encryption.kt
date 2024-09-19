package encryption

interface IEncryptor {
    fun encode(line: String): String
    fun decode(line: String): String
}

class CezareWithKeyWord(alphabet: String, offset: Int, keyword: String): IEncryptor {
    private val alphabet = alphabet
    private val table: String
    init {
        val alphabet_without_keyword_chars = alphabet.filterNot { it in keyword }
        this.table = alphabet_without_keyword_chars.drop(alphabet_without_keyword_chars.length - offset) + keyword + alphabet_without_keyword_chars.dropLast(offset)
    }
    override fun encode(line: String): String {
        val encoded_line = line.map {
            this.get_char_by_table(it, this.table, this.alphabet)
        }.joinToString("")

        return encoded_line
    } 

    override fun decode(line: String): String {
        val decoded_line = line.map {
            this.get_char_by_table(it, this.alphabet, this.table)
        }.joinToString("")

        return decoded_line
    }

    private fun get_char_by_table(char: Char, from: String, by: String): Char =
        from[by.indexOf(char)]
}

class Scytale(row_count: Int): IEncryptor {
    private val row_count = row_count

    override fun encode(line: String): String {
        val col_count = (line.length - 1) / this.row_count + 1
        val table = this.create_encode_table(line, col_count)
        val cols = this.transpose(table)

        return cols.map { it.joinToString("") }.joinToString("")
    }

    override fun decode(line: String): String {
        val col_count = (line.length - 1) / this.row_count + 1
        val table = this.create_decode_table(line, col_count)
        
        return table.map { it.joinToString("") }.joinToString("")
    }

    fun print_table(table: Array<Array<String>>) {
        for (row in table) {
            println(row.joinToString())
        }
    }

    private fun create_encode_table(line: String, col_count: Int, placeholder: String = " "): Array<Array<String>> {
        var line = line + placeholder.repeat(this.row_count * col_count - line.length)
        var table = Array(this.row_count) {
            line
                .drop(it * col_count)
                .dropLast(line.length - (it + 1) * col_count)
                .map { it.toString() }
                .toTypedArray()
        }

        return table
    }

    private fun create_decode_table(line: String, col_count: Int): Array<Array<String>> {
        val table = Array(this.row_count) { row ->
            Array<String>(col_count) { col ->
                val i = row + col * row_count
                line[i].toString()
            }
        }

        return table
    }

    private fun transpose(table: Array<Array<String>>): Array<Array<String>> {
        val col_count = table.first().size
        val cols = Array(col_count) { index ->
            table.map { arr -> arr[index] }.toTypedArray()
        }

        return cols
    }
}