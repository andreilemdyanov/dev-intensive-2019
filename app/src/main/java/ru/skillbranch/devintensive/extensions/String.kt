package ru.skillbranch.devintensive.extensions

fun String.truncate(num: Int = 16): String {
    var result = this.trim()
    if (result.length > num) {
        result = result.substring(0, num).trim().plus("...")
    }
    return result
}