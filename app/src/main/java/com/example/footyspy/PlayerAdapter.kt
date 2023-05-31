package com.example.footyspy

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlayerAdapter (
    private var players: MutableList<Player>,
    private var myParent: StartGameActivity,
    private var nChosen: Int = 0,
    private var LOG_COUNTER: Int = 0,
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvPlayerName: TextView = itemView.findViewById(R.id.tvPlayerName)
        val cbChosen: CheckBox = itemView.findViewById(R.id.cbChosen)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.apply {
            tvPlayerName.text = players[position].name
            cbChosen.isChecked = players[position].isChecked
        }

        holder.cbChosen.setOnCheckedChangeListener {_, isChecked ->
            players[position].isChecked = isChecked
            updatePlayersList(position, isChecked)
        }

        holder.ivDelete.setOnClickListener {
            Log.d("DeleteListener", "Position is $position")
            removePlayer(players[position])
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }

    fun addPlayer(player: Player){
        players.add(player)
        notifyItemInserted(players.size - 1)
        updatePlayersList(players.size - 1, true)
        // notifyDataSetChanged()
    }

    private fun removePlayer(player: Player){
        Log.d("RemovePlayer", "Name is ${player.name}")
        val deletedIndex = players.indexOf(player)
        Log.d("RemovePlayer", "Position is $deletedIndex")
        players.remove(player)
        Log.d("RemovePlayer", "${player.name} is removed in position $deletedIndex.")
        notifyItemRemoved(deletedIndex)
        updatePlayersList()
        notifyDataSetChanged()
    }

    private fun updatePlayersList(position: Int = -1, isChecked: Boolean = false){
        if(position != -1) players[position].isChecked = isChecked
        nChosen = players.count { player -> player.isChecked }
        LOG_COUNTER = (LOG_COUNTER + 1) % 10
        Log.d("UpdatePlayersList", "$LOG_COUNTER. nPlayers = ${players.size}, nChosen = $nChosen, position = $position")
        myParent.updatetvNPlayersChosen(nChosen)
    }
}