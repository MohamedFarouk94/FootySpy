package com.example.footyspy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityStartroundBinding
import java.lang.Integer.max
import java.lang.Integer.min

class StartRoundActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartroundBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartroundBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val game = intent.getSerializableExtra("EXTRA_GAME") as Game

        binding.btnBackFromStartRound.setOnClickListener { finish() }
        binding.ivRight.setOnClickListener{
            val x = binding.etNSpies.text.toString().toInt() + 1
            binding.etNSpies.setText(min(x, game.maxNumberOfSpies).toString())
        }
        binding.ivLeft.setOnClickListener{
            val x = binding.etNSpies.text.toString().toInt() - 1
            binding.etNSpies.setText(max(x, 1).toString())
        }
        binding.tvRoundTitle.text = "Round #${game.nRoundsPlayed + 1}"
        binding.tvSetNSPiesHint.text = "We're ${game.nPlayers} so number of spies should be between 1 and ${game.maxNumberOfSpies}."

        binding.btnNextFromStartRound.setOnClickListener {
            Intent(this, RevealingNameActivity::class.java).also {startActivity(it)}
        }
    }
}