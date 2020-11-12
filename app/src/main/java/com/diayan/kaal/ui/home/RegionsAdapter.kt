package com.diayan.kaal.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.R
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegions
import com.diayan.kaal.databinding.ItemRegionsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class EventsAdapter(private val clickListener: EventClickListener) :
    PagedListAdapter<DataItem, RecyclerView.ViewHolder>(EventsDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    fun addHeaderAndSubmitList(list: PagedList<FirebaseRegions>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.EventsDataItem(it) }
            } as PagedList //note this!

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val eventItem = getItem(position) as DataItem.EventsDataItem
                holder.bind(clickListener, eventItem.event)
            }
        }
    }
}

class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_regions_header, parent, false)
            return HeaderViewHolder(view)
        }
    }
}

class ViewHolder private constructor(private val binding: ItemRegionsBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: EventClickListener, eventItem: FirebaseRegions) {
        binding.regions = eventItem
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRegionsBinding.inflate(layoutInflater, parent, false)

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

class EventClickListener(val clickListener: (event: FirebaseRegions) -> Unit) {
    fun onClick(event: FirebaseRegions) = clickListener(event)
}

sealed class DataItem {
    data class EventsDataItem(val event: FirebaseRegions) : DataItem() {
        override val id = event.id.toLong()
    }

    object Header : DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE

    }

    abstract val id: Long
}
