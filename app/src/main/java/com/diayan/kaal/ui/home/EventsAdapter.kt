package com.diayan.kaal.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.databinding.ItemEventsBinding

class EventsAdapter : PagedListAdapter<Event, EventsAdapter.ViewHolder>(StoreDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEventsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val events = getItem(position)
        events?.let {
            holder.apply {
                
            }
        }
    }

    class ViewHolder(private val binding: ItemEventsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private class StoreDiffCallback : DiffUtil.ItemCallback<Event>() {

        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }
}