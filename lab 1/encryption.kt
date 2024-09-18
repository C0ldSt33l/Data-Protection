package encryption

interface IEncryptor {
    fun encode(line: String): String
    fun decode(line: String): String
}

class CezareWithKeyWord: IEncryptor {
    override fun encode(line: String): String = "Cezare with keyword encode"
    override fun decode(line: String): String = "Cezare with keyword decode"
}

class Scytale(row_count: Int): IEncryptor {
    val row_count = row_count
    // var col_count: Int? = null

    override fun encode(line: String): String {
        val col_count = (line.length - 1) / this.row_count + 1
        println("col count: $col_count")
        val table = this.create_encode_table(line, col_count)
        this.print_table(table)
        println("---------------")

        val cols = this.transpose(table)
        return cols.map { it.joinToString("") }.joinToString("")
    }

    override fun decode(line: String): String {
        val col_count = (line.length - 1) / this.row_count + 1
        val table = this.create_decode_table(line, col_count)

        this.print_table(table)
        
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
        println("line size: ${line.length}")
        line.forEachIndexed { i, _ ->
            print("$i |")
        }
        println("")
        line.forEach {
            print("$it |")
        }
        println("-----------------")
        val table = Array(this.row_count) { row ->
            val tmp = Array<String>(col_count) { col ->
                val i = row + col * row_count
                // println("i $i")
                line[i].toString()
            }
            println(tmp.contentToString())
            tmp
        }
        println("-----------------")
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