package com.diayan.kaal.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.R
import com.diayan.kaal.data.model.firebasemodels.FirebaseEvents
import kotlinx.android.synthetic.main.item_events.view.*

class ExperimentalAdapter(private val eventList: List<FirebaseEvents>) :
    RecyclerView.Adapter<ExperimentalAdapter.EventsViewHolder>() {

    class EventsViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var event: FirebaseEvents? = null

        fun bindEvents(event: FirebaseEvents) {
            this.event = event
            itemView.region_name_textView.text = event.description

            //itemView.description_textView.text = event.description
        }

        companion object {
            private val EVENT_KEY = "EVENT"
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        return EventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = eventList[position]
        holder.bindEvents(event)
    }

    override fun getItemCount() = eventList.size
}
