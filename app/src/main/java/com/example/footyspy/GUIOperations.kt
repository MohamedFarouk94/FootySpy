package com.example.footyspy

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class GUIOperations {
    companion object{
        fun disableButton(btn: Button){
            btn.setBackgroundColor(Color.parseColor("#808080"))
            btn.isEnabled = false
            btn.isClickable = false
        }

        fun enableButton(btn: Button){
            btn.setBackgroundColor(Color.parseColor("#4e0707"))
            btn.isEnabled = true
            btn.isClickable = true
        }

        fun unselectTV(tv: TextView, context: Context){
            tv.background = ContextCompat.getDrawable(context, R.drawable.rounded_view_unselected)
            tv.setTextColor(Color.parseColor("#000000"))
        }

        fun selectTV(tv: TextView, context: Context){
            tv.background = ContextCompat.getDrawable(context, R.drawable.rounded_view_selected)
            tv.setTextColor(Color.parseColor("#ffffff"))
        }

        fun getString(id: Int, resources: Resources, vararg args: Any) : String =
            String.format(resources.getString(id), *args)

        fun getAdjustedTextSize(txt: String) : Float =
            (36 - txt.length).coerceIn(10, 18).toFloat()
    }
}