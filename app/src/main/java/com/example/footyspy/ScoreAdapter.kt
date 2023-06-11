package com.example.footyspy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ScoreAdapter(
    private var listOfPlayers: MutableList<Player>
    ) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    inner class ScoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvRank: TextView = itemView.findViewById(R.id.tvRank)
        val tvPlayerNameScore: TextView = itemView.findViewById(R.id.tvPlayerNameScore)
        val tvLastRoundScore: TextView = itemView.findViewById(R.id.tvLastRoundScore)
        val tvPoints: TextView = itemView.findViewById(R.id.tvPoints)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.tvRank.text = (position + 1).toString()
        holder.tvPlayerNameScore.text = listOfPlayers[position].name
        holder.tvLastRoundScore.text = listOfPlayers[position].roundScore.toString()
        holder.tvPoints.text = listOfPlayers[position].totalScore.toString()
    }

    override fun getItemCount(): Int = listOfPlayers.size
}