package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.komandas_list.view.*

class KomandasAdapter() : RecyclerView.Adapter<KomandasVH>() {

    var komanda = ArrayList<KomandasEntity>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomandasVH =
        KomandasVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.komandas_list, parent, false)
        )


    override fun getItemCount(): Int {
        return komanda.size
    }

    override fun onBindViewHolder(holder: KomandasVH, position: Int) = holder.itemView.run {
        nosaukums.text = komanda[position].nosaukums + ":"
        punkti.text =  "Pamatlaikā: " + komanda[position].uzvarasPamatl + ":" +
                komanda[position].zaudesPamatl
        uzvaras.text = "Papildlaikā: " + komanda[position].uzvarasPapild + ":" +
                komanda[position].zaudesPapild
        zaudes.text = "Vārti: " + komanda[position].iegutieVarti + ":" +
                komanda[position].zaudetieVarti
        punktu_skaits.text = komanda[position].punkti.toString()
    }

}


class KomandasVH(itemView: View) : RecyclerView.ViewHolder(itemView)
