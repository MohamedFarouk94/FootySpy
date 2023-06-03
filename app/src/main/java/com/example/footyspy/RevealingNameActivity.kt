package com.example.footyspy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityRevealingnameBinding
import java.lang.Float.max
import java.lang.Float.min

class RevealingNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRevealingnameBinding
    private lateinit var bitmap: Bitmap
    private var xRatio : Float = 1f
    private var yRatio : Float = 1f

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRevealingnameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bitmap = BitmapFactory.decodeResource(this.resources, R.mipmap.bmp_cat_foreground)
        val mutableBitmap : Bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        binding.ivScratch.setImageBitmap(mutableBitmap)
        val eraser: BitmapEraser = BitmapEraser(mutableBitmap.width.toInt(), mutableBitmap.height.toInt())

        binding.ivScratch.post {
            xRatio = mutableBitmap.width.toFloat() / binding.ivScratch.width.toFloat()
            yRatio = mutableBitmap.height.toFloat() / binding.ivScratch.height.toFloat()
        }

        binding.ivScratch.setOnTouchListener { _, event ->
            view.performClick()
            val x = (event.x.toFloat() * xRatio).toInt()
            val y = (event.y.toFloat() * yRatio).toInt()
            val coordinates = eraser.getPoints(x, y, 20)
            // for (coo in coordinates) mutableBitmap.setPixel(coo.first, coo.second, Color.parseColor("#ff0000"))
            for (coo in coordinates) mutableBitmap.setPixel(coo.first, coo.second, Color.argb(0, 1, 1, 1))
            binding.ivScratch.setImageBitmap(mutableBitmap)
            true
        }
    }
}