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
    val listOfSpies : MutableList<Player> = mutableListOf(),
    var listOfChoices: MutableList<String> = mutableListOf()
) : Serializable {
    fun startRound(context: Context){
        for (player in game.chosenPlayers) player.resetForRound()
        setSpies()
        setSecret(context, topic)
    }

    fun updateScore(investigator: Player, chosenToBeSpies: List<Player>){
        for(player in chosenToBeSpies) if(listOfSpies.contains(player)) investigator.addScoreBy(1)
        for(player in listOfSpies) if(player != investigator && !chosenToBeSpies.contains(player)) player.addScoreBy(1)
    }

    fun areAllAsked(): Boolean = game.chosenPlayers.all { player -> player.nQuestions > 0 }

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
        val listOfWordsShuffled = listOfWords.shuffled()
        secretWord = listOfWordsShuffled[0]
        listOfChoices = listOfWords.slice(1..12).toMutableList()
        Log.d("SecretWord", secretWord)
    }
}