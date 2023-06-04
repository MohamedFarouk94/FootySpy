package com.example.footyspy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        binding.btnBackFromStartGame.setOnClickListener {finish()}
        binding.ivAdd.setOnClickListener{
            val addedPlayerName = binding.etAdd.text.toString()
            if(addedPlayerName == "")
                Toast.makeText(applicationContext, "A player must have a name.", Toast.LENGTH_SHORT).show()
            else if(adapter.doesThisPlayerExist(addedPlayerName))
                Toast.makeText(applicationContext, "This player already exists.", Toast.LENGTH_SHORT).show()
            else {
                binding.etAdd.text.clear()
                adapter.addPlayer(Player(addedPlayerName, true))
            }
        }

        binding.btnNextFromStartGame.setOnClickListener {
            if (adapter.getNChosen() < 3){
                Toast.makeText(applicationContext, "Please check at least 3 players.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val chosenPlayers = adapter.getChosenPlayers()
            val game = Game(chosenPlayers)
            Intent(this, StartRoundActivity::class.java).also {
                it.putExtra("EXTRA_GAME", game)
                startActivity(it)
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
