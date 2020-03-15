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

    val daysEndFirstPlus: MutableList<Int> = ArrayList()
    val daysEndFirstMinus: MutableList<Int> = ArrayList()
    val minutesEndFirstPlus: MutableList<Int> = ArrayList()
    val minutesEndFirstMinus: MutableList<Int> = ArrayList()
    val daysEndSecondPlus: MutableList<Int> = ArrayList()
    val daysEndSecondMinus: MutableList<Int> = ArrayList()
    val minutesEndSecondPlus: MutableList<Int> = ArrayList()
    val minutesEndSecondMinus: MutableList<Int> = ArrayList()

    for (i in 0..365) {
        if (i % 10 in 2..4)
            if (i !in 12..14 && i !in 112..114 && i !in 212..214 && i !in 312..314) {
                daysEndFirstPlus.add(i)
                daysEndFirstMinus.add(i.unaryMinus())
                if (i < 50) {
                    minutesEndFirstPlus.add(i)
                    minutesEndFirstMinus.add(i.unaryMinus())
                }
            }
        if (i % 10 == 1)
            if (i != 11 && i != 111 && i != 211 && i != 311) {
                daysEndSecondPlus.add(i)
                daysEndSecondMinus.add(i.unaryMinus())
                if (i < 50) {
                    minutesEndSecondPlus.add(i)
                    minutesEndSecondMinus.add(i.unaryMinus())
                }
            }
    }

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
                    in daysEndFirstMinus -> "$day дня назад"
                    in daysEndSecondMinus -> "$day день назад"
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
                    in minutesEndFirstMinus -> "$min минуты назад"
                    in minutesEndSecondMinus -> "$min минуту назад"
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
                    in daysEndFirstPlus -> "через $day дня"
                    in daysEndSecondPlus -> "через $day день"
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
                    in minutesEndFirstPlus -> "через $min минуты"
                    in minutesEndSecondPlus -> "через $min минуту"
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