package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    fun unreadableMessageCount(): Int {
        messages.filter { !it.isReaded }
        return messages.size
    }

    fun lastMessageDate(): Date? =
        if(messages.isEmpty()) null
        else  messages.last().date


    fun lastMessageShort(): Pair<String, String> {
        if (messages.isEmpty()) return "Собщений еще нет" to "@John_Doe"
       return when (val lastMessage = messages.last()) {
            is TextMessage -> lastMessage.text.toString() to lastMessage.from.firstName.toString()
            is ImageMessage -> "${lastMessage.from.firstName} - отправил фото" to lastMessage.from.firstName.toString()
            else -> "Собщений еще нет" to "@John_Doe"
        }
    }

    private fun isSingle(): Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        val user = members.first()
        return if (isSingle()) {
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                user.isOnline
            )
        } else {
            ChatItem(
                id,
                null,
                Utils.toInitials(user.firstName, "") ?: "??",
                title,
                lastMessageShort().second,
                unreadableMessageCount(),
                lastMessageDate()?.shortFormat(),
                false,
                ChatType.GROUP
            )
        }
    }

}

enum class ChatType{
    SINGLE,
    GROUP,
    ARCHIVE
}


