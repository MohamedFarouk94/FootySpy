package com.example.footyspy

import android.util.Log
import java.io.Serializable

class RoundEngine (
    val round : Round,
    private val listOfSpies : MutableList<Player> = mutableListOf(),
    ) : Serializable{

    fun startRound(){
        for (player in round.game.chosenPlayers) {
            player.isSpy = false
            player.nQuestions = 0
        }
        setSpies()
        // Here we will choose the secret word
    }

    fun endRound(){
        // Todo
    }

    fun ask(playerName : String){
        val targetPlayer : Player? = round.game.chosenPlayers.find { player -> player.name == playerName}
        targetPlayer?.let {targetPlayer.nQuestions++}
    }

    private fun setSpies(){
        var ids = (0 until round.game.nPlayers).toMutableList()
        var shuffledIds = ids.shuffled()
        for (i in 0 until round.nSpies){
            val player = round.game.chosenPlayers[shuffledIds[i]]
            player.isSpy = true
            listOfSpies.add(player)
            Log.d("Spy", "${player.name}")
        }
    }
}