package com.example.footyspy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
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

        binding.btnBackFromStartRound.setOnClickListener {
            AlertDialog.Builder(this).showCustomAlert(getString(R.string.back_alert), this) { finish() }
        }
        binding.ivRight.setOnClickListener{
            val x = binding.etNSpies.text.toString().toInt() + 1
            binding.etNSpies.text = min(x, game.maxNumberOfSpies).toString()
        }
        binding.ivLeft.setOnClickListener{
            val x = binding.etNSpies.text.toString().toInt() - 1
            binding.etNSpies.text = max(x, 1).toString()
        }
        binding.tvRoundTitle.text = GUIOperations.getString(R.string.round_n, resources, game.nRoundsPlayed + 1)
        binding.tvSetNSPiesHint.text = GUIOperations.getString(R.string.set_n_spies_hint, resources, game.nPlayers, game.maxNumberOfSpies)

        binding.btnNextFromStartRound.setOnClickListener {
            val round = Round(game,
                binding.etNSpies.text.toString().toInt(),
                findViewById<RadioButton>(binding.rgRoundTopic.checkedRadioButtonId).text.toString().lowercase().replace(" ", "_"))
            Log.d("Round", "${round.nSpies} --- ${round.topic}")
            Intent(this, RevealingNameActivity::class.java).also {
                it.putExtra("EXTRA_ROUND", round)
                startActivity(it)
                finish()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this).showCustomAlert(getString(R.string.back_alert), this) { finish() }
    }
}