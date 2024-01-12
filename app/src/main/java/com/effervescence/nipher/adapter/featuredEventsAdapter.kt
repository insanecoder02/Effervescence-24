package com.effervescence.nipher.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.effervescence.nipher.R
import com.effervescence.nipher.dataClass.FeaturedEventes
import com.effervescence.nipher.fragments.hf2

class featuredEventsAdapter(
    private val items: List<FeaturedEventes>, private val itemClickListener: hf2
) : RecyclerView.Adapter<featuredEventsAdapter.FeaturedEventesViewHolder>() {

    interface boxo {
        fun OnItemClick(item: FeaturedEventes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedEventesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.featuredeventlayout, parent, false)
        return FeaturedEventesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedEventesViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class FeaturedEventesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageOfEvent = itemView.findViewById<ImageView>(R.id.featuredEventImageView)

        fun bind(featuredEventes: FeaturedEventes) {

            val roundedCorners = RoundedCorners(20)
            val requestOptions = RequestOptions().transform(roundedCorners)


            Glide.with(itemView.context).load(featuredEventes.url).apply(requestOptions)
                .placeholder(R.drawable.whilte_broder) // Add a placeholder image
                .error(R.drawable.image_svgrepo_com) // Add an error image
                .into(imageOfEvent)
        }
    }
}
