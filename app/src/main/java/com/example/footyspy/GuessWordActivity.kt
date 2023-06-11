package com.example.footyspy

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.footyspy.databinding.ActivityGuesswordBinding
import kotlin.math.round

class GuessWordActivity: AppCompatActivity() {
    private lateinit var binding: ActivityGuesswordBinding
    private var chosenAnswer: String = "NOT_SELECTED"
    private var listOfChoicesViews: MutableList<TextView> = mutableListOf()
    private var currentSpyID: Int = 0

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuesswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round

        for (i in 0..11) {
            val currTvId: Int = resources.getIdentifier("tvChoice$i", "id", packageName)
            val currTv = findViewById<TextView>(currTvId)
            currTv.text = round.listOfChoices[i]
            currTv.setTextSize(COMPLEX_UNIT_SP, GUIOperations.getAdjustedTextSize(round.listOfChoices[i]))
            listOfChoicesViews.add(currTv)

            currTv.setOnClickListener{
                for (tv in listOfChoicesViews) GUIOperations.unselectTV(tv, this)
                GUIOperations.selectTV(currTv, this)
                chosenAnswer = currTv.text.toString()
            }
        }

        binding.btnNextFromGuessWord.setOnClickListener {
            if(chosenAnswer == "NOT_SELECTED") return@setOnClickListener // Toast
            round.updateScoreGuess(round.listOfSpies[currentSpyID], chosenAnswer)
            currentSpyID++
            if(currentSpyID == round.nSpies) {
                Intent(this, UncoveringActivity::class.java).also {
                    it.putExtra("EXTRA_ROUND", round)
                    startActivity(it)
                    finish()
                    return@setOnClickListener
                }
            }
            restartScreen(round)
        }

        restartScreen(round)
    }

    private fun restartScreen(round: Round){
        for(tv in listOfChoicesViews) GUIOperations.unselectTV(tv, this)
        chosenAnswer = "NOT_SELECTED"
        val nextSpyID = currentSpyID + 1
        binding.tvAboveChoices.text = GUIOperations.getString(R.string.above_choices, resources, round.listOfSpies[currentSpyID].name)
        binding.tvBelowChoices.text =
            if(nextSpyID == round.nSpies) GUIOperations.getString(R.string.below_choices_final, resources)
            else GUIOperations.getString(R.string.below_choices, resources, round.listOfSpies[nextSpyID].name)
    }
}