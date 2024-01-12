package com.effervescence.nipher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.effervescence.nipher.R
import com.effervescence.nipher.dataClass.DevelopersList

class DevelopersAdapter(
    val devmem: MutableList<DevelopersList>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<DevelopersAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: DevelopersList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.developerlayout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return devmem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = devmem[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var name: TextView = itemView.findViewById(R.id.nameTextView)
        private var designation: TextView = itemView.findViewById(R.id.desginations)
        private var image: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(developer: DevelopersList) {

            name.text = developer.name
            designation.text = developer.Position
            Glide.with(itemView.context).load(R.drawable.github_mark_white).into(image)

//            itemView.setOnClickListener {
//                clickListener.onItemClick(developer)
//            }
        }

    }
}