package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.komandas_list.view.*

class VartsarguAdapter() : RecyclerView.Adapter<VartsarguVH>() {

    var speletaji = ArrayList<Vartusargs>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VartsarguVH =
        VartsarguVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.komandas_list, parent, false)
        )


    override fun getItemCount(): Int {
        return speletaji.size
    }

    override fun onBindViewHolder(holder: VartsarguVH, position: Int) = holder.itemView.run {
        nosaukums.text = speletaji[position].vards + " " + speletaji[position].uzvards
        punkti.text =  speletaji[position].komanda
        punktu_skaits.text = String.format("%.2f", speletaji[position].attieciba)
    }

}

data class Vartusargs (var vards: String, var uzvards: String, var gamesPlayed: Int, var ielaistie: Int,
                       var attieciba: Double, var komanda: String)
class VartsarguVH(itemView: View) : RecyclerView.ViewHolder(itemView)
