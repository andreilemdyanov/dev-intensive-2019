package ru.skillbranch.devintensive.models

import android.util.Log
import androidx.core.text.isDigitsOnly

data class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var count = 0
    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    private fun errorResponse(): String = when (question) {
        Question.NAME -> "Имя должно начинаться с заглавной буквы"
        Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
        Question.MATERIAL -> "Материал не должен содержать цифр"
        Question.BDAY -> "Год моего рождения должен содержать только цифры"
        Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
        Question.IDLE -> ""
    }

    private fun validateAnswer(answer: String): Boolean =
        when (question) {
            Question.NAME -> {
                question.answers.contains(answer.capitalize())
            }
            Question.PROFESSION -> {
                question.answers.contains(answer.decapitalize())
            }
            Question.MATERIAL -> {
                Regex("""\d+""").containsMatchIn(answer)
            }
            Question.BDAY -> {
                !answer.isDigitsOnly()
            }
            Question.SERIAL -> {
                !(answer.isDigitsOnly() && answer.length == 7)
            }
            Question.IDLE -> true

        }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question == Question.IDLE) question.question to status.color
        else if (question.answers.contains(answer)) {
//            count = 0
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        } else if (validateAnswer(answer)) "${errorResponse()}\n${question.question}" to status.color
        else {
            count++
            Log.d("M_Bender", "count = $count")
            status = status.nextStatus()
            if (count > 3) {
                count = 0
                question = Question.NAME
                status = Status.NORMAL
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else "Это неправильный ответ\n${question.question}" to status.color
        }

    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "Bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}