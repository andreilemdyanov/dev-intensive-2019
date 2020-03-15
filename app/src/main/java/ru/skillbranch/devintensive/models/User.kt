package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

class User constructor(
    var id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int,
    var respect: Int,
    var lastVisit: Date?,
    var isOnline: Boolean
) {

    data class Builder(
        var lastId: Int = -1,
        var id: String = "$lastId++",
        var firstName: String? = null,
        var lastName: String? = null,
        var avatar: String? = null,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
    ) {

        fun id(value: String) = apply { id = value }
        fun firstName(value: String?) = apply { firstName = value }
        fun lastName(value: String?) = apply { lastName = value }
        fun avatar(value: String) = apply { avatar = value }
        fun rating(value: Int) = apply { rating = value }
        fun respect(value: Int) = apply { respect = value }
        fun lastVisit(value: Date) = apply { lastVisit = value }
        fun isOnline(value: Boolean) = apply { isOnline = value }

        fun build(): User {
            return User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
        }
    }

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)
            return Builder()
                .id("$lastId")
                .firstName(firstName)
                .lastName(lastName)
                .build()
        }
    }
}