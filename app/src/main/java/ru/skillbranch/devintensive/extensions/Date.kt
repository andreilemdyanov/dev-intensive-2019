package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var dif = this.time - date.time
    val positive = dif >= 0
    //Поправка
    if (positive) dif += 1000L

    val day = dif / DAY
    dif %= DAY
    val hour = dif / HOUR
    dif %= HOUR
    val min = dif / MINUTE
    dif %= MINUTE
    val sec = dif / SECOND
    var result: String
    if (!positive) {
        result =
            if (day < -360) "более года назад"
            else if ((day == -1L && hour < -2) || (-360 < day && day < -2))
                when (day.toInt()) {
                    in arrayOf(
                        -2, -3, -4, -22, -23, -24, -32, -33, -34, -42, -43, -44, -52, -53, -54, -62,
                        -63, -64, -72, -73, -74, -82, -83, -84, -92, -93, -94, -102, -103, -104, -112,
                        -113, -114, -122, -123, -124, -132, -133, -134, -142, -143, -144, -152, -153,
                        -154, -162, -163, -164, -172, -173, -174, -182, -183, -184, -192, -193, -194,
                        -202, -203, -204, -212, -213, -214, -222, -223, -224, -232, -233, -234, -242,
                        -243, -244, -252, -253, -254, -262, -263, -264, -272, -273, -274, -282, -283,
                        -284, -292, -293, -294, -302, -303, -304, -312, -313, -314, -322, -323, -324,
                        -332, -333, -334, -342, -343, -344, -352, -353, -354, -362, -363, -364
                        ) -> "$day дня назад"
                    in arrayOf(
                        -1, -21, -31, -41, -51, -61, -71, -81, -91, -101, -121, -131, -141, -151,
                        -161, -171, -181, -191, -201, -221, -231, -241, -251, -261, -271, -281, -291,
                        -301, -321, -331, -341, -351, -361
                    ) -> "$day день назад"
                    else -> "$day дней назад"
                }
            else if (hour < -22 || (day == -1L && hour > -2)) "день назад"
            else if ((hour == -1L && min < -15) || (-22 < hour && hour < -2)) {
                when (hour.toInt()) {
                    in arrayOf(-2, -3, -4, -22) -> "$hour часа назад"
                    in arrayOf(-1, -21) -> "$hour час назад"
                    else -> "$hour часов назад"
                }
            } else if (min < -45 || (hour == -1L && min > -15)) "час назад"
            else if ((min == -1L && sec < -15) || (-45 < min && min < -2)) {
                when (min.toInt()) {
                    in arrayOf(-2, -3, -4, -22, -23, -24, -32, -33, -34, -42, -43, -44) -> "$min минуты назад"
                    in arrayOf(-1, -21, -31, -41) -> "$min минуту назад"
                    else -> "$min минут назад"
                }
            } else if (sec < -45 || (min == -1L && sec > -15)) "минуту назад"
            else if (-45 < sec && sec < -1) "несколько секунд назад"
            else "только что"

    } else {
        result =
            if (day > 360) "более чем через год"
            else if ((day == 1L && hour > 2) || day in 2..360)
                when (day.toInt()) {
                    in arrayOf(
                        2, 3, 4, 22, 23, 24, 32, 33, 34, 42, 43, 44, 52, 53, 54, 62,
                        63, 64, 72, 73, 74, 82, 83, 84, 92, 93, 94, 102, 103, 104, 112,
                        113, 114, 122, 123, 124, 132, 133, 134, 142, 143, 144, 152, 153,
                        154, 162, 163, 164, 172, 173, 174, 182, 183, 184, 192, 193, 194,
                        202, 203, 204, 212, 213, 214, 222, 223, 224, 232, 233, 234, 242,
                        243, 244, 252, 253, 254, 262, 263, 264, 272, 273, 274, 282, 283,
                        284, 292, 293, 294, 302, 303, 304, 312, 313, 314, 322, 323, 324,
                        332, 333, 334, 342, 343, 344, 352, 353, 354, 362, 363, 364
                    ) -> "через $day дня"
                    in arrayOf(
                        1, 21, 31, 41, 51, 61, 71, 81, 91, 101, 121, 131, 141, 151,
                        161, 171, 181, 191, 201, 221, 231, 241, 251, 261, 271, 281, 291,
                        301, 321, 331, 341, 351, 361
                    ) -> "через $day день"
                    else -> "через $day дней"
                }
            else if (hour > 22 || (day == 1L && hour < 2)) "через день"
            else if ((hour == 1L && min > 15) || hour in 2..22) {
                when (hour.toInt()) {
                    in arrayOf(2, 3, 4, 22) -> "через $hour часа"
                    in arrayOf(1, 21) -> "через $hour час"
                    else -> "через $hour часов"
                }
            } else if (min > 45 || (hour == 1L && min < 15)) "через час"
            else if ((min == 1L && sec > 15) || min in 2..45) {
                when (min.toInt()) {
                    in arrayOf(2, 3, 4, 22, 23, 24, 32, 33, 34, 42, 43, 44) -> "через $min минуты"
                    in arrayOf(1, 21, 31, 41) -> "через $min минуту"
                    else -> "через $min минут"
                }
            } else if (sec > 45 || (min == 1L && sec < 15)) "через минуту"
            else if (sec in 1..45) "через несколько секунд"
            else "только что"
    }
    result = result.replace("-", "")
    println(result)
    return result
}


enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}