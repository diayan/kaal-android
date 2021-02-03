package com.diayan.kaal.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.diayan.kaal.R
import com.diayan.kaal.data.model.firebasemodels.FirebaseRegions
import com.diayan.kaal.databinding.ItemRegionsBinding
import com.diayan.kaal.databinding.ItemRegionsHeaderBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class EventsAdapter(val clickListener: RegionsClickListener) :
    ListAdapter<EventsAdapter.DataItem, RecyclerView.ViewHolder>(RegionsDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> RegionsHeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> RegionsViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is RegionsViewHolder -> {
                val region = getItem(position) as DataItem.RegionsItem
                holder.apply {
                    bind(clickListener, region.region)
                }
            }

            is RegionsHeaderViewHolder -> {
                val regionsHeader = getItem(position) as DataItem.Header
                holder.apply {
                    bind(clickListener, regionsHeader.region)
                }
            }
        }
    }

    class RegionsHeaderViewHolder private constructor(var binding: ItemRegionsHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: RegionsClickListener, item: FirebaseRegions) {
            binding.region = item
            binding.regionNameTextView.text = item.name
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RegionsHeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRegionsHeaderBinding.inflate(layoutInflater, parent, false)
                return RegionsHeaderViewHolder(binding)
            }
        }
    }

    class RegionsViewHolder private constructor(var binding: ItemRegionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: RegionsClickListener, item: FirebaseRegions) {
            binding.regions = item
            val imgUri = item.imageUrl.toUri().buildUpon().scheme("https").build()
            binding.apply {
                invalidateAll()
                binding.regionNameTextView.text = item.name
                //binding.regionCoverImageView
                Glide.with(binding.regionCoverImageView.context)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                    )
                    .into(binding.regionCoverImageView)
                binding.clickListener = clickListener
                binding.executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): RegionsViewHolder {
                val layoutInflater = LayoutInflater.from((parent.context))
                val binding = ItemRegionsBinding.inflate(layoutInflater, parent, false)
                return RegionsViewHolder(binding)
            }
        }
    }

    private class RegionsDiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }

    sealed class DataItem {
        data class RegionsItem(val region: FirebaseRegions) : DataItem() {
            override val id: Int = region.id
        }

        data class Header(val region: FirebaseRegions) : DataItem() {
            override val id = region.id
        }

        abstract val id: Int
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.RegionsItem -> ITEM_VIEW_TYPE_ITEM
            else -> throw IndexOutOfBoundsException() //I added this so it just says something useful in case
        }
    }

    //convert regions list to DataItem in the adapter
    fun addHeaderAndSubmitList(list: List<FirebaseRegions>?) {
        val headerList = mutableListOf<FirebaseRegions>() //this list takes the header items, in this case just one item!
        val mainList = mutableListOf<FirebaseRegions>()
        list?.forEach {
            if (it.id == 1) {
                headerList.add(it)
            } else if(it.id != 1){
                mainList.add(it)
            }
        }

        adapterScope.launch {
            val items =
                headerList.map { DataItem.Header(it) } + mainList.map { DataItem.RegionsItem(it) }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    class RegionsClickListener(val clickListener: (tripId: Int) -> Unit) {
        fun onClick(region: FirebaseRegions) = clickListener(region.id)
    }
}

