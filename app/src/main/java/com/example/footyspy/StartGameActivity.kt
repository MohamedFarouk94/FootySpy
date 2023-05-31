package com.example.footyspy

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyspy.databinding.ActivityStartgameBinding
import java.lang.Exception

class StartGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartgameBinding
    private lateinit var adapter: PlayerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("StartGame", "Inside the onCreate function.")
        super.onCreate(savedInstanceState)
        binding = ActivityStartgameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var players = mutableListOf<Player>(Player("Farouk", false),
            Player("Gehan", false))
        adapter = PlayerAdapter(players, this)
        binding.rvPlayers.adapter = adapter
        binding.rvPlayers.layoutManager = LinearLayoutManager(this)

        binding.btnBack1.setOnClickListener {finish()}
        binding.ivAdd.setOnClickListener{
            val addedPlayerName = binding.etAdd.text.toString()
            if(addedPlayerName != "") {
                binding.etAdd.text.clear()
                adapter.addPlayer(Player(addedPlayerName, true))
            }
        }
    }

    fun updatetvNPlayersChosen(nChosen: Int){
        val mainText = "$nChosen players chosen"
        val tail = "(minimum 3)"
        if(nChosen < 3){
            binding.tvNPlayersChosen.text = mainText + tail
            binding.tvNPlayersChosen.setTextColor(Color.parseColor("#4e0707"))
        }
        else{
            binding.tvNPlayersChosen.text = mainText
            binding.tvNPlayersChosen.setTextColor(Color.parseColor("#00ff00"))
        }
    }
}
