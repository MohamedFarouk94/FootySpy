package com.example.footyspy

import java.io.Serializable

data class Game(
    val chosenPlayers: List<Player>,
    var nRoundsPlayed: Int = 0,
    val nPlayers: Int = chosenPlayers.size,
    val maxNumberOfSpies: Int = nPlayers / 2,
    val previousSecretWords: MutableList<String> = mutableListOf()
) : Serializable