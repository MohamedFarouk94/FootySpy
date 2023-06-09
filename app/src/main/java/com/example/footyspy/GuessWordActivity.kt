package com.example.footyspy

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityGuesswordBinding

class GuessWordActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGuesswordBinding
    private lateinit var chosenAnswer: String

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuesswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round

        for (word in round.listOfChoices){
            val tv = TextView(this)
            tv.width = 80
            tv.height = 20
            tv.setPadding(16,16,16,16)
            tv.setBackgroundColor(Color.parseColor("#ffffff"))
            tv.text = word
            tv.setOnClickListener{Log.d("GL", word)}
            binding.glChoices.addView(tv)
        }
    }
}