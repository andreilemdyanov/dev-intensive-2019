package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {

    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (currentFocus == null)
        inputMethodManager.hideSoftInputFromWindow(View(this).windowToken, 0)
    else
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
}

fun Activity.getRootView(): View {
    return findViewById(android.R.id.content)
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    this.getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    return heightDiff > 0
}

fun Activity.isKeyboardClosed(): Boolean {
    return !this.isKeyboardOpen()
}
