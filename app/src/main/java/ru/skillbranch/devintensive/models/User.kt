package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(private val config: Builder) {
    private var introBit: String

    init {
        introBit = getIntro()
        println(
            "It's Alive!!!\n" +
                    "${if (lastName == "Doe") "His name is $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n" +
                    "${getIntro()}"
        )
    }

    private fun getIntro(): String = "$firstName $lastName"

    fun printMe() = println(
        """
            id: $id
            firstName: $firstName
            lastName: $lastName
            avatar: $avatar
            rating: $rating
            respect: $respect
            lastVisit: $lastVisit
            isOnline: $isOnline
        """.trimIndent()
    )

    companion object Builder {
        protected var lastId: Int = -1
        var id: String = "$lastId++"
        var firstName: String? = null
        var lastName: String? = null
        var avatar: String? = null
        var rating: Int = 0
        var respect: Int = 0
        var lastVisit: Date? = Date()
        var isOnline: Boolean = false

        fun id(value: Int) = apply { id = "$value" }
        fun firstName(value: String) = apply { firstName = value }
        fun lastName(value: String) = apply { lastName = value }
        fun avatar(value: String) = apply { avatar = value }
        fun rating(value: Int) = apply { rating = value }
        fun respect(value: Int) = apply { respect = value }
        fun lastVisit(value: Date) = apply { lastVisit = value }
        fun isOnline(value: Boolean) = apply { isOnline = value }

        fun build(): User {
            return User(this)
        }
    }
}