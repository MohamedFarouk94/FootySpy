package com.example.footyspy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityStartgameBinding

class StartGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartgameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartgameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBack1.setOnClickListener {finish()}
    }
}