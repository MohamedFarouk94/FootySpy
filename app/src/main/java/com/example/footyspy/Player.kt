package com.example.footyspy

import java.io.Serializable

data class Player (
    val name : String,
    var isChecked : Boolean,

    var isSpy : Boolean = false,
    var nQuestions : Int = 0
    ) : Serializable