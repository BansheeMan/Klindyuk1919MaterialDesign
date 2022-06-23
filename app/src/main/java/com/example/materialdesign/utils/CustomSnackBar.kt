package com.example.materialdesign.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.example.materialdesign.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(text: String?, actionText: Int, action: (View) -> Unit) {
    text?.let {
        Snackbar.make(this, it, Snackbar.LENGTH_SHORT).run {
            setBackgroundTint(ContextCompat.getColor(context, R.color.black))
            setTextColor(ContextCompat.getColor(context, R.color.red_100))
            setText(it)
            action(actionText, R.color.red_900, action)
            show()
        }
    }
}

fun Snackbar.action(action: Int, color: Int, listener: (View) -> Unit) {
    setActionTextColor(ContextCompat.getColor(context, color))
    setAction(action, listener)
}