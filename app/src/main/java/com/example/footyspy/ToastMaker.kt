package com.example.footyspy

import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast

fun Toast.showCustomToast(message: String, activity: Activity)
{
    val layout = activity.layoutInflater.inflate (
        R.layout.custom_toast,
        activity.findViewById(R.id.custom_toast)
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.tvToast)
    textView.text = message

    // use the application extension function
    @Suppress("DEPRECATION")
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 180)
        duration = Toast.LENGTH_SHORT
        view = layout
        show()
    }
}