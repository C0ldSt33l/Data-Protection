package lab3

import java.io.File

fun String.get_bigramms(): MutableList<String> {
    val bigramms = mutableListOf<String>()
    for (i in 0..<this.length - 1) {
        bigramms.add(this.slice(i..i + 1))
    }

    return bigramms
}

class Test {
    val test_keywords = arrayOf(
        "ПРЕФЕКТУРА",
        "СТАРТ",
        "МАТЧ",
        "СПОРТ",
        "ФУТБОЛ",
        "САМУРАЙ",
        "КАТАНА",
        "РИТМ",
        "ИГРА",
        "БАЛАНС",
        "ДВА СЛОВА",
        "ГОЛОВОЛОМКА",
        "ТОРТ",
        "СТАТИСТИКА",
        "ДВИЖОК"
    )
    val offsets = Array<Double>(test_keywords.size) { 0.0 }

    fun run() {
        val encoded = Playflay(
            "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,-:!?",
            5, 8,
            "ПРЕФЕКТУРА"
        ).encode(File("text.txt").readLines().joinToString(""))
        for ((i, keyword) in this.test_keywords.withIndex()) {
            val statistic: MutableMap<String, Double> = mutableMapOf(
                "ВА" to 0.0,
                "ДА" to 0.0,
                "КА" to 0.0,
                "НА" to 0.0,
                "РА" to 0.0,
                "ТА" to 0.0,
                "ОВ" to 0.0,
                "ОГ" to 0.0,
                "ОД" to 0.0,
                "ВЕ" to 0.0,
                "ДЕ" to 0.0,
                "МЕ" to 0.0,
                "НЕ" to 0.0,
                "РЕ" to 0.0,
                "ТЕ" to 0.0,
                "КИ" to 0.0,
                "НИ" to 0.0,
                "РИ" to 0.0,
                "ТИ" to 0.0,
                "ОЙ" to 0.0,
                "СК" to 0.0,
                "АЛ" to 0.0,
                "ЕЛ" to 0.0,
                "ОЛ" to 0.0,
                "ОМ" to 0.0,
                "АН" to 0.0,
                "ЕН" to 0.0,
                "ИН" to 0.0,
                "ОН" to 0.0,
                "ВО" to 0.0,
                "ГО" to 0.0,
                "КО" to 0.0,
                "НО" to 0.0,
                "ПО" to 0.0,
                "РО" to 0.0,
                "СО" to 0.0,
                "ТО" to 0.0,
                "АР" to 0.0,
                "ЕР" to 0.0,
                "ОР" to 0.0,
                "ПР" to 0.0,
                "ЕС" to 0.0,
                "ИС" to 0.0,
                "ОС" to 0.0,
                "АТ" to 0.0,
                "ЕТ" to 0.0,
                "ИТ" to 0.0,
                "ОТ" to 0.0,
                "СТ" to 0.0,
                "НЫ" to 0.0,
            )
            val shifr = Playflay(
                "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ .,-:!?",
                5, 8,
                keyword
            )
            val decoded = shifr.decode(encoded)
            val bigramms = decoded.get_bigramms()
            for (bigramm in bigramms) {
                if (!standart_bigramm_statistic_short.containsKey(bigramm)) continue
                val prevVal = statistic.get(bigramm) as Double
                statistic.put(bigramm, prevVal + 1)
            }

            for (entry in statistic.entries.iterator()) {
                val key = entry.key
                val value = entry.value

                val chance = (value / bigramms.size.toDouble())
                val res = (chance - standart_bigramm_statistic_short[key] as Double / 1000.0)
                this.offsets[i] = this.offsets[i] as Double + (res * res)
            }
            println(statistic.map { "${it.key}: ${it.value}" }.joinToString("|"))
        }

        for ((i, keyword) in this.test_keywords.withIndex()) {
            println("Keyword: $keyword, offset: ${this.offsets[i]}")
        }
        val winKeyword = this.test_keywords[this.offsets.indexOf(this.offsets.min())]
        println("\nMOST PROBABLE KEYWORD: $winKeyword")
    }
}