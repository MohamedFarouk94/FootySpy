package com.example.footyspy

import android.app.Activity
import android.app.AlertDialog

fun AlertDialog.Builder.showCustomAlert(message: String, activity: Activity, onPosFunction: () -> Unit){
    this.apply {
        setMessage(message)
        setCancelable(false)
        setNegativeButton("No") {_, _ -> return@setNegativeButton}
        setPositiveButton("Yes") {_, _ -> onPosFunction(); return@setPositiveButton}
        show()
    }
}