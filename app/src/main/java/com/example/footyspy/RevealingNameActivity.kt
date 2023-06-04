package com.example.footyspy

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
        val roundEngine = RoundEngine(round)
        roundEngine.startRound()

        val bitmap: Bitmap = BitmapFactory.decodeResource(this.resources, R.mipmap.bmp_cat_foreground)
        val mutableBitmap : Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        binding.ivScratch.setImageBitmap(mutableBitmap)
        val eraser: BitmapEraser = BitmapEraser(mutableBitmap.width.toInt(), mutableBitmap.height.toInt())
        restartScreen(view, roundEngine, bitmap)

        binding.btnNextFromRevealingName.setOnClickListener {
            currentPlayerId++
            restartScreen(view, roundEngine, bitmap)
        }
    }

    private fun restartScreen(view: ConstraintLayout, roundEngine: RoundEngine, bitmap: Bitmap){
        val mutableBitmap : Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val nPlayers = roundEngine.round.game.nPlayers
        binding.ivScratch.setImageBitmap(mutableBitmap)
        eraser = BitmapEraser(mutableBitmap.width.toInt(), mutableBitmap.height.toInt())
        val currentPlayerName = roundEngine.round.game.chosenPlayers[currentPlayerId % nPlayers].name
        val nextPlayerName = roundEngine.round.game.chosenPlayers[(currentPlayerId + 1) % nPlayers].name
        val secretName = if(roundEngine.round.game.chosenPlayers[currentPlayerId % nPlayers].isSpy) "SPY @_@" else "Hello!"

        binding.tvAboveImage.text = "Hello $currentPlayerName, scratch the image below gently to reveal the secret name.\nBe careful that no one looks!"
        binding.tvBelowImage.text = "Only if you're sure that you get the name\n(Or if you're the spy!)\nPress Next!\nAnd give the phone to $nextPlayerName"
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