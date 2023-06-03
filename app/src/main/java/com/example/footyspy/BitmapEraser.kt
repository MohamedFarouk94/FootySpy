package com.example.footyspy

import android.util.Log

class BitmapEraser (
    private val width : Int,
    private val height: Int,
    private val coordinates: MutableList<Pair<Int, Int>> = mutableListOf(),
    private val boolMap : Array<Array<Boolean>> = Array(width) { Array(height) {false} }
    ){
    private fun isValid(x : Int, y : Int) : Boolean{
        if(x < 1 || y < 1) return false
        if(x >= width || y >= height) return false
        return !boolMap[x][y]
    }

    private fun getNeighbors(x: Int, y: Int, level : Int){
        Log.d("Eraser", "x = $x, y = $y, level = $level")
        if(!isValid(x, y)) return
        coordinates.add(Pair(x, y))
        boolMap[x][y] = true
        val steps = listOf(1, -1, 0)
        if(level < 1) return
        for (stepx in steps) for (stepy in steps) getNeighbors(x + stepx, y + stepy, level - 1)
    }

    fun getPoints (x: Int, y: Int, level: Int) : List<Pair<Int, Int>> {
        Log.d("Eraser", "$width, $height")
        coordinates.clear()
        getNeighbors(x, y, level)
        return coordinates
    }
}