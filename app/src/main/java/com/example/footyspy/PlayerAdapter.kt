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
            players[holder.adapterPosition].isChecked = isChecked
            updatePlayersList(holder.adapterPosition, isChecked)
        }

        holder.ivDelete.setOnClickListener {
            removePlayer(holder.adapterPosition)
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

    private fun removePlayer(position: Int){
        // val deletedIndex = players.indexOf(player)
        // players.remove(player)
        players.removeAt(position)
        notifyItemRemoved(position)
        updatePlayersList()
        // notifyDataSetChanged()
    }

    private fun updatePlayersList(position: Int = -1, isChecked: Boolean = false){
        if(position != -1) players[position].isChecked = isChecked
        nChosen = players.count { player -> player.isChecked }
        myParent.updatetvNPlayersChosen(nChosen)
    }

    fun getChosenPlayers(): List<Player>{
        return players.filter { player: Player -> player.isChecked}
    }

    fun getNChosen() : Int{
        return players.count { player -> player.isChecked }
    }

    fun doesThisPlayerExist(name : String) : Boolean{
        return players.any {player ->  player.name == name}
    }

    fun getAllPlayersNames() : MutableList<String>{
        return players.map {it.name}.toMutableList()
    }
}