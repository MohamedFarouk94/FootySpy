package com.example.footyspy

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.footyspy.databinding.ActivityUncoveringBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UncoveringActivity: AppCompatActivity() {
    private lateinit var binding: ActivityUncoveringBinding
    private var jobDone: Boolean = false

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUncoveringBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round

        val aboveCover =
            if(round.finalStage) GUIOperations.getString(R.string.above_cover_secret, resources)
            else GUIOperations.getString(R.string.above_cover_spy, resources)

        val belowCover =
            if(round.finalStage) GUIOperations.getString(R.string.below_cover_secret, resources)
            else GUIOperations.getString(R.string.below_cover_spy, resources)

        val cover =
            if(round.finalStage) round.secretWord else round.listOfSpies.joinToString("\n") {player -> player.name}

        val nextActivity = if(round.finalStage) ScoreActivity::class.java else GuessWordActivity::class.java

        binding.tvAboveCover.text = aboveCover
        binding.tvUncover.text = ""
        binding.tvBelowCover.text = ""

        binding.btnRevealingSpies.setOnClickListener {
            if(jobDone) {
                Intent(this, nextActivity).also {
                    round.finalStage = true
                    it.putExtra("EXTRA_ROUND", round)
                    startActivity(it)
                    finish()
                    return@setOnClickListener
                }
            }

           GlobalScope.launch(Dispatchers.Main) {
               GUIOperations.disableButton(binding.btnRevealingSpies)

               binding.tvUncover.text = "3"
               delay(1000)
               binding.tvUncover.text = "2"
               delay(1000)
               binding.tvUncover.text = "1"
               delay(1000)
               binding.tvUncover.textSize = 40f
               binding.tvUncover.setTextColor(Color.parseColor("#4e0707"))
               binding.tvUncover.background = getDrawable(R.drawable.toast_background)
               binding.tvUncover.text = cover

               GUIOperations.enableButton(binding.btnRevealingSpies)

               binding.btnRevealingSpies.text = getString(R.string.next)
               binding.tvBelowCover.text = belowCover

               jobDone = true
           }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this).showCustomAlert(getString(R.string.back_alert), this) { finish() }
    }
}