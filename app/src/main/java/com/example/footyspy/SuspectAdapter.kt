package com.example.footyspy

import android.app.Application
import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SuspectAdapter(
    private var suspectPlayers: MutableList<Player>,
    private var nSpies: Int,
    private var myParent: QuestionsActivity,
    private var askedFlags: MutableList<Boolean> = MutableList(suspectPlayers.size) {false},
    private var nAsked: Int = 0
    ) : RecyclerView.Adapter<SuspectAdapter.SuspectViewHolder>() {

    inner class SuspectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvSuspect: TextView = itemView.findViewById(R.id.tvSuspect)
        val context: Context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuspectViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_suspect, parent, false)
        return SuspectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuspectViewHolder, position: Int) {
        holder.tvSuspect.text = GUIOperations.getString(R.string.suspect, myParent.resources, suspectPlayers[position].name, suspectPlayers[position].nQuestions)
        //"${suspectPlayers[position].name} (${suspectPlayers[position].nQuestions})"
        // holder.tvSuspect.setTextSize(TypedValue.COMPLEX_UNIT_SP, GUIOperations.getAdjustedTextSize(suspectPlayers[position].name))

        holder.tvSuspect.setOnClickListener{
            if(askedFlags[holder.adapterPosition]) {
                GUIOperations.unselectTV(holder.tvSuspect, myParent)
                nAsked--
                askedFlags[holder.adapterPosition] = false
            }
            else if(nAsked < nSpies){
                GUIOperations.selectTV(holder.tvSuspect, myParent)
                nAsked++
                askedFlags[holder.adapterPosition] = true
            }
            else{
                Toast(myParent).showCustomToast(GUIOperations.getString(R.string.already_n_spies, myParent.resources, nSpies), myParent)
            }
        }
    }

    override fun getItemCount(): Int = suspectPlayers.size

    fun getNAsked(): Int = nAsked

    fun getAskedPlayers(): MutableList<Player>{
        val askedPlayers = mutableListOf<Player>()
        for (i in 0 until suspectPlayers.size) if(askedFlags[i]) askedPlayers.add(suspectPlayers[i])
        return askedPlayers
    }
}