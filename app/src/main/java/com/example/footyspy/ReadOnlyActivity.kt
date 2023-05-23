package com.example.footyspy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.footyspy.databinding.ActivityReadonlyBinding

class ReadOnlyActivity : AppCompatActivity(){
    private lateinit var binding: ActivityReadonlyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadonlyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnBack2.setOnClickListener {finish()}

        val clickedButton = intent.getStringExtra("EXTRA_BUTTON")
        binding.textView2.text = "Here will be a $clickedButton page."
    }
}