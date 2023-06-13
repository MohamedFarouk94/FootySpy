package com.example.footyspy

import android.app.AlertDialog
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
                Toast(this).showCustomToast(GUIOperations.getString(R.string.less_than_n_spies, resources, round.nSpies), this)
                return@setOnClickListener
            }

            if(investigation)
                try {for (player in adapter.getAskedPlayers()) player.asked()} catch (_: Exception){}
            else
                round.updateScoreInvestigate(round.game.chosenPlayers[currentPlayerId], adapter.getAskedPlayers())

            if(!investigation && currentPlayerId == round.game.nPlayers - 1){
                Intent(this, UncoveringActivity::class.java).also {
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
                Toast(this).showCustomToast(getString(R.string.cannot_move), this)
                return@setOnClickListener
            }
            AlertDialog.Builder(this).showCustomAlert(getString(R.string.majority_alert), this){
                currentPlayerId = 0
                investigation = false
                GUIOperations.disableButton(binding.btnWeGotThem)
                restartScreenDeciding(round)
            }
        }
    }

    private fun restartScreenInvestigation(round: Round){
        val nextPlayerId = (currentPlayerId + 1) % round.game.nPlayers
        val currentPlayerName = round.game.chosenPlayers[currentPlayerId].name
        val nextPlayerName = round.game.chosenPlayers[nextPlayerId].name
        binding.tvAboveQuestions.text = GUIOperations.getString(R.string.above_questions_investigations, resources, currentPlayerName, round.nSpies)
        binding.tvBelowQuestions.text = GUIOperations.getString(R.string.below_questions_investigations, resources, nextPlayerName)
        adapter = SuspectAdapter(round.game.chosenPlayers.filter { player -> player.name != currentPlayerName }.sortedBy{player -> -player.nQuestions}.toMutableList(),
            round.nSpies, this)
        binding.rvSuspectPlayers.adapter = adapter
        binding.rvSuspectPlayers.layoutManager = LinearLayoutManager(this)
    }

    private fun restartScreenDeciding(round: Round){
        val nextPlayerId = (currentPlayerId + 1) % round.game.nPlayers
        val currentPlayerName = round.game.chosenPlayers[currentPlayerId].name
        val nextPlayerName = round.game.chosenPlayers[nextPlayerId].name
        binding.tvAboveQuestions.text = GUIOperations.getString(R.string.above_questions_deciding, resources, currentPlayerName, round.nSpies)
        binding.tvBelowQuestions.text =
            if(nextPlayerId != 0)
                GUIOperations.getString(R.string.below_questions_deciding, resources, nextPlayerName)
            else
                GUIOperations.getString(R.string.below_questions_deciding_final, resources)

        adapter = SuspectAdapter(round.game.chosenPlayers.filter { player ->  player.name != currentPlayerName}.sortedBy { player ->  -player.nQuestions}.toMutableList(),
            round.nSpies, this)
        binding.rvSuspectPlayers.adapter = adapter
        binding.rvSuspectPlayers.layoutManager = LinearLayoutManager(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this).showCustomAlert(getString(R.string.back_alert), this) { finish() }
    }

}