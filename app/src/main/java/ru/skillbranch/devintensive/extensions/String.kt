package ru.skillbranch.devintensive.extensions

fun String.truncate(num: Int = 16): String {
    var result = this.trim()
    if (result.length > num) {
        result = result.substring(0, num).trim().plus("...")
    }
    return result
}

fun String.stripHtml(): String {
    val start = indexOf(">") + 1
    val end = lastIndexOf("<")
    var result = substring(start, end)
    result = result.replace("\\s+".toRegex(), " ")
    result = result.replace("&", "")
    result = result.replace("\"", "")
    result = result.replace("\'", "")

    return result
}