package com.example.footyspy

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
    }
}