package com.example.footyspy

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityReadonlyBinding

class ReadOnlyActivity : AppCompatActivity(){
    private lateinit var binding: ActivityReadonlyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadonlyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBackFromReadOnly.setOnClickListener {finish()}

        val shownText =
            when(intent.getStringExtra("EXTRA_BUTTON")){
                "How To Play" -> getString(R.string.how_to_play_content)
                "Credits" -> getString(R.string.credits_content)
                else -> getString(R.string.contact_content)
            }

        binding.textView2.text = shownText
        binding.textView2.movementMethod = ScrollingMovementMethod()
    }
}