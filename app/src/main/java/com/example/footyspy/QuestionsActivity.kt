package com.example.footyspy

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyspy.databinding.ActivityQuestionsBinding
import java.lang.Exception

class QuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionsBinding
    private var currentPlayerId: Int = 0
    private lateinit var adapter: SuspectAdapter

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round
        restartScreen(round)

        binding.btnNextFromQuestions.setOnClickListener {
            if(adapter.getNAsked() < round.nSpies) {
                Toast.makeText(
                    applicationContext,
                    "You have to ask ${round.nSpies} players before clicking next.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            currentPlayerId = (currentPlayerId + 1) % round.game.nPlayers
            try {
                for (player in adapter.getAskedPlayers()) player.asked()
            } catch (e: Exception){}
            restartScreen(round)
        }
    }

    private fun restartScreen(round: Round){
        val nextPlayerId = (currentPlayerId + 1) % round.game.nPlayers
        val currentPlayerName = round.game.chosenPlayers[currentPlayerId].name
        val nextPlayerName = round.game.chosenPlayers[nextPlayerId].name
        binding.tvAboveQuestions.text = String.format(resources.getString(R.string.above_questions), currentPlayerName, round.nSpies)
        binding.tvBelowQuestions.text = String.format(resources.getString(R.string.below_questions), nextPlayerName)
        adapter = SuspectAdapter(round.game.chosenPlayers.filter { player -> player.name != currentPlayerName }.sortedBy{player -> -player.nQuestions}.toMutableList()
            ,round.nSpies, this)
        binding.rvSuspectPlayers.adapter = adapter
        binding.rvSuspectPlayers.layoutManager = LinearLayoutManager(this)
    }
}