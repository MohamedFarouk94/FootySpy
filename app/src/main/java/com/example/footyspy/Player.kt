package com.example.footyspy

import java.io.Serializable

class Player (
    val name : String,
    var isChecked : Boolean,

    var isSpy : Boolean = false,
    var nQuestions : Int = 0,

    var roundScore : Int = 0,
    var totalScore : Int = 0,
    ) : Serializable{
        fun resetForRound(){
            isSpy = false
            nQuestions = 0
            roundScore = 0
        }

        fun asked(){
            nQuestions++
        }

        fun addScoreBy(x: Int){
            roundScore += x
        }

        fun updateTotalScore(){
            totalScore += roundScore
        }
    }