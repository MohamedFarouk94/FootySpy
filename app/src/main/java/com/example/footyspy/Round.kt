package com.example.footyspy

import java.io.Serializable

data class Round (
    val game: Game,
    val nSpies: Int,
    val topic: String,
    val index: Int = game.nRoundsPlayed + 1,
) : Serializable