package com.andrii.costsmanager.presentation

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager



/**
 * Created by Andrii Medvid on 8/10/2019.
 */

fun Activity.hideKeyboard() {
    this.currentFocus?.let { view ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}