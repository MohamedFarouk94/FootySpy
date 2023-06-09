package com.example.footyspy

import android.content.Intent
import android.graphics.Color
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
    private var investigation: Boolean = true
    private lateinit var adapter: SuspectAdapter

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round
        restartScreenInvestigation(round)

        binding.btnNextFromQuestions.setOnClickListener {
            if(adapter.getNAsked() < round.nSpies) {
                Toast.makeText(
                    applicationContext,
                    "You have to choose ${round.nSpies} players before clicking next.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if(investigation)
                try {for (player in adapter.getAskedPlayers()) player.asked()} catch (_: Exception){}
            else
                round.updateScore(round.game.chosenPlayers[currentPlayerId], adapter.getAskedPlayers())

            if(!investigation && currentPlayerId == round.game.nPlayers - 1){
                Intent(this, RevealingSpiesActivity::class.java).also {
                    it.putExtra("EXTRA_ROUND", round)
                    startActivity(it)
                    finish()
                    return@setOnClickListener
                }
            }

            currentPlayerId = (currentPlayerId + 1) % round.game.nPlayers
            if(investigation) restartScreenInvestigation(round) else restartScreenDeciding(round)
        }

        binding.btnWeGotThem.setOnClickListener {
            if(!investigation) return@setOnClickListener

            if(!round.areAllAsked()){
                Toast.makeText(
                    applicationContext,
                    "Cannot move to this stage without everyone being asked.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            currentPlayerId = 0
            investigation = false
            binding.btnWeGotThem.isClickable = false
            binding.btnWeGotThem.isEnabled = false
            binding.btnWeGotThem.setBackgroundColor(Color.parseColor("#808080"))
            restartScreenDeciding(round)
        }
    }

    private fun restartScreenInvestigation(round: Round){
        val nextPlayerId = (currentPlayerId + 1) % round.game.nPlayers
        val currentPlayerName = round.game.chosenPlayers[currentPlayerId].name
        val nextPlayerName = round.game.chosenPlayers[nextPlayerId].name
        binding.tvAboveQuestions.text = String.format(resources.getString(R.string.above_questions), currentPlayerName, round.nSpies)
        binding.tvBelowQuestions.text = String.format(resources.getString(R.string.below_questions), nextPlayerName)
        adapter = SuspectAdapter(round.game.chosenPlayers.filter { player -> player.name != currentPlayerName }.sortedBy{player -> -player.nQuestions}.toMutableList(),
            round.nSpies, this)
        binding.rvSuspectPlayers.adapter = adapter
        binding.rvSuspectPlayers.layoutManager = LinearLayoutManager(this)
    }

    private fun restartScreenDeciding(round: Round){
        val nextPlayerId = (currentPlayerId + 1) % round.game.nPlayers
        val currentPlayerName = round.game.chosenPlayers[currentPlayerId].name
        val nextPlayerName = round.game.chosenPlayers[nextPlayerId].name
        binding.tvAboveQuestions.text = "Hey, $currentPlayerName, Who are the spies? choose ${round.nSpies} that you suspect."
        binding.tvBelowQuestions.text =
            if(nextPlayerId != 0)
                "After deciding, click Next and give the phone to $nextPlayerName"
            else
                "After deciding, click Next to discover the truth."

        adapter = SuspectAdapter(round.game.chosenPlayers.filter { player ->  player.name != currentPlayerName}.sortedBy { player ->  player.nQuestions}.toMutableList(),
            round.nSpies, this)
        binding.rvSuspectPlayers.adapter = adapter
        binding.rvSuspectPlayers.layoutManager = LinearLayoutManager(this)
    }

}