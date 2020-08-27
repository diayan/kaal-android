package com.diayan.kaal.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.R
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.databinding.ItemEventsBinding


private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class EventsAdapter(clickListener: EventClickListener) :
    PagedListAdapter<DataItem, RecyclerView.ViewHolder>(EventsDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val events = getItem(position)
        events?.let {
            holder.apply {
            }
        }
    }
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.header, parent, false)
            return HeaderViewHolder(view)
        }
    }
}

class ViewHolder private constructor(private val binding: ItemEventsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemEventsBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)
        }
    }
}

private class EventsDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
}

class EventClickListener(val clickListener: (event: Event) -> Unit) {
    fun onClick(event: Event) = clickListener(event)
}

sealed class DataItem {
    data class EventsDataItem(val event: Event) : DataItem() {
        override val id = event.id.toLong()
    }

    object Header : DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE

    }

    abstract val id: Long
}
