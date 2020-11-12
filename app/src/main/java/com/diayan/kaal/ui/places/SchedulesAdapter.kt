package com.diayan.kaal.ui.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.data.model.Schedules
import com.diayan.kaal.databinding.ItemSchedulesBinding

class SchedulesAdapter : PagedListAdapter<Schedules, SchedulesAdapter.ViewHolder>(StoreDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSchedulesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val places = getItem(position)
        places?.let {
            holder.apply {

            }
        }
    }

    class ViewHolder(private val binding: ItemSchedulesBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private class StoreDiffCallback : DiffUtil.ItemCallback<Schedules>() {

        override fun areItemsTheSame(oldItem: Schedules, newItem: Schedules): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedules, newItem: Schedules): Boolean {
            return oldItem == newItem
        }
    }
}