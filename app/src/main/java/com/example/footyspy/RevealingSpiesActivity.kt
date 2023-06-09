package com.example.footyspy

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityRevealingspiesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RevealingSpiesActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRevealingspiesBinding
    private var spiesRevealed: Boolean = false

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRevealingspiesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round

        binding.tvAboveCover.text = "The time has come.\nIf you want to know who are the spies, click the button below."
        binding.tvUncover.text = ""
        binding.tvBelowCover.text = ""

        binding.btnRevealingSpies.setOnClickListener {
            if(spiesRevealed) {
                Intent(this, GuessWordActivity::class.java).also {
                    it.putExtra("EXTRA_ROUND", round)
                    startActivity(it)
                    finish()
                }
            }

           GlobalScope.launch(Dispatchers.Main) {
               binding.btnRevealingSpies.setBackgroundColor(Color.parseColor("#808080"))
               binding.btnRevealingSpies.isEnabled = false
               binding.btnRevealingSpies.isClickable = false

               binding.tvUncover.text = "3"
               delay(1000)
               binding.tvUncover.text = "2"
               delay(1000)
               binding.tvUncover.text = "1"
               delay(1000)
               binding.tvUncover.textSize = 30f
               binding.tvUncover.setTextColor(Color.parseColor("#4e0707"))
               binding.tvUncover.text = round.listOfSpies.joinToString("\n") {player -> player.name}

               binding.btnRevealingSpies.setBackgroundColor(Color.parseColor("#4e0707"))
               binding.btnRevealingSpies.isEnabled = true
               binding.btnRevealingSpies.isClickable = true
               binding.btnRevealingSpies.text = getString(R.string.next)

               binding.tvBelowCover.text = "Click Next so the spies can guess what was the secret word."

               spiesRevealed = true
           }
        }
    }
}