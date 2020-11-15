package com.diayan.kaal.ui.schedules

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.data.model.firebasemodels.FirebaseSchedules
import com.diayan.kaal.databinding.ItemScheduledHeaderBinding
import com.diayan.kaal.databinding.ItemSchedulesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class SchedulesAdapter(val clickListener: TripClickListener) :
    ListAdapter<SchedulesAdapter.DataItem, RecyclerView.ViewHolder>(ScheduleDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> ScheduleHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ScheduleViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ScheduleViewHolder -> {
                val scheduledTrip = getItem(position) as DataItem.ScheduleItem
                holder.apply {
                    bind(clickListener, scheduledTrip.schedule)
                }
            }

            is ScheduleHeaderViewHolder -> {
                val scheduleHeader = getItem(position) as DataItem.Header
                holder.apply {
                    bind(clickListener, scheduleHeader.schedule)
                }
            }
        }
    }

    class ScheduleHeaderViewHolder private constructor(var binding: ItemScheduledHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: TripClickListener, item: FirebaseSchedules) {
            binding.trip = item
            binding.destinationTextView.text = item.destination
            binding.timeRemainingTextView.text = item.destination
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ScheduleHeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemScheduledHeaderBinding.inflate(layoutInflater, parent, false)
                return ScheduleHeaderViewHolder(binding)
            }
        }
    }

    class ScheduleViewHolder private constructor(var binding: ItemSchedulesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: TripClickListener, item: FirebaseSchedules) {
            binding.trip = item
            binding.apply {
                invalidateAll()
                destinationTextView.text = item.destination
                tripDateTextView.text = item.destination
                itemClickListener = clickListener
                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ScheduleViewHolder {
                val layoutInflater = LayoutInflater.from((parent.context))
                val binding = ItemSchedulesBinding.inflate(layoutInflater, parent, false)
                return ScheduleViewHolder(binding)
            }
        }
    }

    private class ScheduleDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {
        data class ScheduleItem(val schedule: FirebaseSchedules) : DataItem() {
            override val id: Int = schedule.id
        }

        data class Header(val schedule: FirebaseSchedules) : DataItem() {
            override val id = schedule.id
        }

        abstract val id: Int
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ScheduleItem -> ITEM_VIEW_TYPE_ITEM
            else -> throw IndexOutOfBoundsException() //I added this so it just says something useful in case
        }
    }

    //convert schedule list to DataItem in the adapter
    fun addHeaderAndSubmitList(list: List<FirebaseSchedules>?) {
        val headerList =
            mutableListOf<FirebaseSchedules>() //this list takes the header items, in this case just one item!
        list?.forEach {
            if (it.id == 1) {
                headerList.add(it)
            }
        }

        adapterScope.launch {
            val items =
                headerList.map { DataItem.Header(it) } + list!!.map { DataItem.ScheduleItem(it) }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class TripClickListener(val clickListener: (tripId: Int) -> Unit) {
        fun onClick(trip: FirebaseSchedules) = clickListener(trip.id)
    }
}