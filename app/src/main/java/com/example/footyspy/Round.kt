package com.example.footyspy

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.Serializable

class Round (
    val game: Game,
    val nSpies: Int,
    val topic: String,
    val index: Int = game.nRoundsPlayed + 1,
    var secretWord: String = "",
    private val listOfSpies : MutableList<Player> = mutableListOf()
) : Serializable {
    fun startRound(context: Context){
        for (player in game.chosenPlayers) {
            player.isSpy = false
            player.nQuestions = 0
        }
        setSpies()
        setSecret(context, topic)
    }

    private fun setSpies(){
        var ids = (0 until game.nPlayers).toMutableList()
        var shuffledIds = ids.shuffled()
        for (i in 0 until nSpies){
            val player = game.chosenPlayers[shuffledIds[i]]
            player.isSpy = true
            listOfSpies.add(player)
            Log.d("Spy", player.name)
        }
    }

    private fun setSecret(context: Context, topic: String){
        Log.d("SecretWord", topic)

        val bufferedReader: BufferedReader = context.assets.open("$topic.txt").bufferedReader()
        val commaSeparatedWords = bufferedReader.use { it.readText() }
        val listOfWords = commaSeparatedWords.split(",")
        secretWord = listOfWords.random().trim()
        Log.d("SecretWord", secretWord)
    }
}