package com.diayan.kaal.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.R
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegion
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_regions.view.*

class RegionsAdapter(private val eventList: List<FirebaseRegion>, private val header: View) :
    RecyclerView.Adapter<RegionsAdapter.EventsViewHolder>() {
    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

    fun isHeader(position: Int): Boolean {
        return position == 0
    }

    class EventsViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var event: FirebaseRegion? = null

        fun bindEvents(event: FirebaseRegion) {
            this.event = event
            itemView.region_name_textView.text = event.name
            if (event.imageUrl.isNotEmpty()) {
                Picasso.get().load(event.imageUrl).into(itemView.region_cover_imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return EventsViewHolder(header)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_regions, parent, false)
        return EventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        if (isHeader(position)) {
            return
        }
        val event = eventList[position - 1] //subtract one for the item_regions_header
        holder.bindEvents(event)
    }

    override fun getItemViewType(position: Int): Int {
        return if (isHeader(position)) ITEM_VIEW_TYPE_HEADER else ITEM_VIEW_TYPE_ITEM
    }

    override fun getItemCount() = eventList.size
}

