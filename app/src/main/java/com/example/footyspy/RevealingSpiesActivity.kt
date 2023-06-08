package com.example.footyspy

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityRevealingspiesBinding
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
            if(spiesRevealed) return@setOnClickListener// here will be the next activity

            binding.btnRevealingSpies.setBackgroundColor(Color.parseColor("#808080"))
            binding.btnRevealingSpies.isEnabled = false
            binding.btnRevealingSpies.isClickable = false

            //**DisplayingCounting**//

            binding.btnRevealingSpies.setBackgroundColor(Color.parseColor("#4e0707"))
            binding.btnRevealingSpies.isEnabled = true
            binding.btnRevealingSpies.isClickable = true
            binding.btnRevealingSpies.text = getString(R.string.next)
        }

    }
}