package com.example.footyspy

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyspy.databinding.ActivityScoreBinding

class ScoreActivity: AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    private lateinit var adapter: ScoreAdapter

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round
        round.updateTotalScore()
        adapter = ScoreAdapter(round.game.chosenPlayers.sortedBy { player -> -player.totalScore }.toMutableList())
        binding.rvScore.adapter = adapter
        binding.rvScore.layoutManager = LinearLayoutManager(this)

        binding.btnEndGame.setOnClickListener {
            AlertDialog.Builder(this).showCustomAlert(getString(R.string.back_alert), this) { finish() }
        }
        binding.btnAnotherRound.setOnClickListener {
            Intent(this, StartRoundActivity::class.java).also {
                round.endRound()
                it.putExtra("EXTRA_GAME", round.game)
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