package ru.skillbranch.devintensive.utils

object Utils {
    private val map = mapOf(
        "a" to "a",
        "б" to "b",
        "в" to "v",
        "г" to "g",
        "д" to "d",
        "е" to "e",
        "ё" to "e",
        "ж" to "zh",
        "з" to "z",
        "и" to "i",
        "й" to "i",
        "к" to "k",
        "л" to "l",
        "м" to "m",
        "н" to "n",
        "о" to "o",
        "п" to "p",
        "р" to "r",
        "с" to "s",
        "т" to "t",
        "у" to "u",
        "ф" to "f",
        "х" to "h",
        "ц" to "c",
        "ч" to "ch",
        "ш" to "sh",
        "щ" to "sh",
        "ъ" to "",
        "ы" to "i",
        "ь" to "",
        "э" to "e",
        "ю" to "yu",
        "я" to "ya",
        "А" to "A",
        "Б" to "B",
        "В" to "V",
        "Г" to "G",
        "Д" to "D",
        "Е" to "E",
        "Ё" to "E",
        "Ж" to "Zh",
        "З" to "Z",
        "И" to "I",
        "Й" to "I",
        "К" to "K",
        "Л" to "L",
        "М" to "M",
        "Н" to "N",
        "О" to "O",
        "П" to "P",
        "Р" to "R",
        "С" to "S",
        "Т" to "T",
        "У" to "U",
        "Ф" to "F",
        "Х" to "H",
        "Ц" to "C",
        "Ч" to "Ch",
        "Ш" to "Sh",
        "Щ" to "Sh",
        "Ъ" to "",
        "Ы" to "I",
        "Ь" to "",
        "Э" to "E",
        "Ю" to "Yu",
        "Я" to "Ya"
    )

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")

        val firstName = if (parts?.getOrNull(0).isNullOrBlank()) null else parts?.getOrNull(0)
        val lastName = if (parts?.getOrNull(1).isNullOrBlank()) null else parts?.getOrNull(1)
        return firstName to lastName

    }

    fun transliteration(payload: String, divider: String = " "): String {
        var str = ""
        for (element in payload) {
            str += if (map[element.toString()] == null) element.toString()
            else map[element.toString()]
        }
        str = str.replace(" ", divider)
        return str
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
            null
        } else if (firstName.isNullOrBlank()) {
            lastName?.get(0)?.toUpperCase().toString()
        } else if (lastName.isNullOrBlank()) {
            firstName[0].toUpperCase().toString()
        } else {
            firstName[0].toUpperCase().toString().plus(
                lastName[0].toUpperCase().toString()
            )

        }

    }

}