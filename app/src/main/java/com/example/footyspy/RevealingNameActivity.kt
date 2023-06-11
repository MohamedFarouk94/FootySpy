package com.example.footyspy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.footyspy.databinding.ActivityRevealingnameBinding
import java.lang.Float.max
import java.lang.Float.min

class RevealingNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRevealingnameBinding
    private lateinit var eraser: BitmapEraser
    private var currentPlayerId: Int = 0
    private var xRatio : Float = 1f
    private var yRatio : Float = 1f

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRevealingnameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val round = intent.getSerializableExtra("EXTRA_ROUND") as Round
        round.startRound(this)

        val bitmap: Bitmap = BitmapFactory.decodeResource(this.resources, R.mipmap.bmp_cat_foreground)
        val mutableBitmap : Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        binding.ivScratch.setImageBitmap(mutableBitmap)
        val eraser: BitmapEraser = BitmapEraser(mutableBitmap.width.toInt(), mutableBitmap.height.toInt())
        restartScreen(view, round, bitmap)

        binding.btnNextFromRevealingName.setOnClickListener {
            currentPlayerId++
            if(currentPlayerId < round.game.nPlayers) restartScreen(view, round, bitmap)
            else{
                Intent(this, QuestionsActivity::class.java).also {
                    it.putExtra("EXTRA_ROUND", round)
                    startActivity(it)
                    finish()
                }
            }
        }
    }

    private fun restartScreen(view: ConstraintLayout, round: Round, bitmap: Bitmap){
        val mutableBitmap : Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val nPlayers = round.game.nPlayers
        binding.ivScratch.setImageBitmap(mutableBitmap)
        eraser = BitmapEraser(mutableBitmap.width.toInt(), mutableBitmap.height.toInt())
        val currentPlayerName = round.game.chosenPlayers[currentPlayerId % nPlayers].name
        val nextPlayerName = round.game.chosenPlayers[(currentPlayerId + 1) % nPlayers].name
        val secretName = if(round.game.chosenPlayers[currentPlayerId % nPlayers].isSpy) "SPY \uD83D\uDE20" else round.secretWord

        binding.tvAboveImage.text = String.format(resources.getString(R.string.above_image), currentPlayerName)
        binding.tvBelowImage.text = String.format(resources.getString(R.string.below_image), nextPlayerName)
        binding.tvSecret.text = secretName


        binding.ivScratch.post {
            xRatio = mutableBitmap.width.toFloat() / binding.ivScratch.width.toFloat()
            yRatio = mutableBitmap.height.toFloat() / binding.ivScratch.height.toFloat()
        }

        binding.ivScratch.setOnTouchListener { _, event ->
            view.performClick()
            val x = (event.x.toFloat() * xRatio).toInt()
            val y = (event.y.toFloat() * yRatio).toInt()
            val coordinates = eraser.getPoints(x, y, 20)
            for (coo in coordinates) mutableBitmap.setPixel(coo.first, coo.second, Color.argb(0, 1, 1, 1))
            binding.ivScratch.setImageBitmap(mutableBitmap)
            true
        }
    }
}