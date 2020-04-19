package ru.skillbranch.devintensive.extensions

import android.util.Log
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.utils.UserView
import ru.skillbranch.devintensive.utils.Utils

fun User.toUserView(): UserView {
    Log.d("M_User", "firstname = $firstName, lastName = $lastName")
    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = if(lastVisit == null) "Еще ни разу не был" else if (isOnline) "online" else "Последний раз был ${lastVisit!!.humanizeDiff()}"
    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        initials = initials,
        avatar = avatar,
        status = status
    )
}


