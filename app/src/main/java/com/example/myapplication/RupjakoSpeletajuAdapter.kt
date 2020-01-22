package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.komandas_list.view.*

class RupjakoSpeletajuAdapter() : RecyclerView.Adapter<RupjakoSpeletajuVH>() {

    var speletaji = ArrayList<SpeletajsEntity>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RupjakoSpeletajuVH =
        RupjakoSpeletajuVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.komandas_list, parent, false)
        )


    override fun getItemCount(): Int {
        return speletaji.size
    }

    override fun onBindViewHolder(holder: RupjakoSpeletajuVH, position: Int) = holder.itemView.run {
        nosaukums.text = speletaji[position].vards + " " + speletaji[position].uzvards
        punkti.text =  speletaji[position].komanda
        punktu_skaits.text = speletaji[position].sodi.toString()
    }

}


class RupjakoSpeletajuVH(itemView: View) : RecyclerView.ViewHolder(itemView)
