package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {

    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (currentFocus == null)
        inputMethodManager.hideSoftInputFromWindow(View(this).windowToken, 0)
    else
        inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
}