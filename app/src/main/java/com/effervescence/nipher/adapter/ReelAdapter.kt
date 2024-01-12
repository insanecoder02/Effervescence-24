package com.effervescence.nipher.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.effervescence.nipher.R
import com.effervescence.nipher.dataClass.FeaturedEventes
import com.effervescence.nipher.dataClass.reelDataClass
import com.effervescence.nipher.fragments.hf2

class ReelAdapter(
    private val items: List<reelDataClass>, private val reelClickListener: hf2
) : RecyclerView.Adapter<ReelAdapter.ReelViewHolder>() {

    interface boxo {
        fun OnItemClick(item: FeaturedEventes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReelViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.reelllayot, parent, false)
        return ReelViewHolder(view)
    }
    override fun onBindViewHolder(holder: ReelViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            reelClickListener.onReelItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ReelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val ReelName = itemView.findViewById<TextView>(R.id.ReelName)
        fun bind(reel: reelDataClass) {
            ReelName.text = reel.reelName
        }
    }
}
