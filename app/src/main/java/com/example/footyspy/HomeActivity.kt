package com.example.footyspy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.footyspy.databinding.ActivityHomeBinding
import com.example.footyspy.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnStartGame.setOnClickListener {
            Log.d("MAIN", "start game is clicked.")
            Intent(this, StartGameActivity::class.java).also{startActivity(it)}
        }

        binding.btnHowToPlay.setOnClickListener {
            Log.d("MAIN", "how to play is clicked.")
            Intent(this, ReadOnlyActivity::class.java).also{
                it.putExtra("EXTRA_BUTTON", "How To Play")
                startActivity(it)}
        }

        binding.btnCredits.setOnClickListener {
            Log.d("MAIN", "credits is clicked.")
            Intent(this, ReadOnlyActivity::class.java).also{
                it.putExtra("EXTRA_BUTTON", "Credits")
                startActivity(it)}
        }

        binding.btnContact.setOnClickListener {
            Log.d("MAIN", "contact is clicked.")
            Intent(this, ReadOnlyActivity::class.java).also{
                it.putExtra("EXTRA_BUTTON", "Contact")
                startActivity(it)}
        }
    }
}