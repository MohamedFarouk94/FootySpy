package com.example.footyspy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.footyspy.databinding.ActivityStartgameBinding

class StartGameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartgameBinding
    private lateinit var adapter: PlayerAdapter
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("StartGame", "Inside the onCreate function.")
        super.onCreate(savedInstanceState)
        binding = ActivityStartgameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPref = getSharedPreferences("savedPlayers", Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        val commaSeparatedPlayersNames = sharedPref.getString("commaSeparatedPlayersNames", null)
        var players = commaSeparatedPlayersNames?.split(",")?.map {Player(it, false)}?.toMutableList()
        if(players == null) players = mutableListOf()
        if(players.size == 1 && players[0].name == "") players = mutableListOf()

        adapter = PlayerAdapter(players, this)
        binding.rvPlayers.adapter = adapter
        binding.rvPlayers.layoutManager = LinearLayoutManager(this)
        binding.tvNPlayersChosen.text = String.format(resources.getString(R.string.choose_players_hint_tail_combined), 0)

        binding.btnBackFromStartGame.setOnClickListener {finish()}
        binding.ivAdd.setOnClickListener{
            val addedPlayerName = binding.etAdd.text.toString().trim()
            if(addedPlayerName == "")
                Toast(this).showCustomToast(getString(R.string.player_empty), this)
            else if (addedPlayerName.toList().any { ch -> "!#?,&{}<>$+=`'\"".toList().contains(ch) })
                Toast(this).showCustomToast(getString(R.string.forbidden_character), this)
            else if(adapter.doesThisPlayerExist(addedPlayerName))
                Toast(this).showCustomToast(getString(R.string.player_exists), this)
            else {
                binding.etAdd.text.clear()
                adapter.addPlayer(Player(addedPlayerName, true))
            }
        }

        binding.btnNextFromStartGame.setOnClickListener {
            if (adapter.getNChosen() < 3){
                Toast(this).showCustomToast(getString(R.string.not_enough), this)
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

    override fun onPause() {
        super.onPause()
        editor.apply{
            val commaSeparatedPlayerNames = adapter.getAllPlayersNames().joinToString(separator = ",")
            putString("commaSeparatedPlayersNames", commaSeparatedPlayerNames)
            apply()
        }
    }

    fun updateTVNPlayersChosen(nChosen: Int){
        if(nChosen < 3){
            binding.tvNPlayersChosen.text = String.format(resources.getString(R.string.choose_players_hint_tail_combined), nChosen)
            binding.tvNPlayersChosen.setTextColor(Color.parseColor("#4e0707"))
        }
        else{
            binding.tvNPlayersChosen.text = String.format(resources.getString(R.string.choose_players_hint), nChosen)
            binding.tvNPlayersChosen.setTextColor(Color.parseColor("#00ff00"))
        }
    }
}
