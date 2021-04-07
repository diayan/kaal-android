package com.diayan.kaal.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.R
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegion

class DetailAdapter(private val context: Context, private val regionList: List<FirebaseRegion>) :
    RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_detail_sites, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(regionList[position])
    }

    override fun getItemCount(): Int = regionList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val placeImageView = itemView.findViewById<ImageView>(R.id.placeImageView)
        val placeNameTextView = itemView.findViewById<TextView>(R.id.placeNameTextView)
        val locationTextView  = itemView.findViewById<TextView>(R.id.locationNameTextView)
        val routeTextView     = itemView.findViewById<TextView>(R.id.routesTextView)
        val distanceTextView  = itemView.findViewById<TextView>(R.id.distanceTextView)
        val estimatedTimeTextView = itemView.findViewById<TextView>(R.id.estimatedTimeTextView)

        fun bind(region: FirebaseRegion) {
            placeNameTextView.text = region.name
            locationTextView.text  = region.location
        }
    }
}