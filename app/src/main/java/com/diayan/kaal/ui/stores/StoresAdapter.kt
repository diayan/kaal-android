package com.diayan.kaal.ui.stores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.diayan.kaal.data.model.Store
import com.diayan.kaal.databinding.ItemStoresBinding

class StoresAdapter : PagedListAdapter<Store, StoresAdapter.ViewHolder>(StoreDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStoresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stores = getItem(position)
        stores?.let {
            holder.apply {
            }
        }
    }

    class ViewHolder(private val binding: ItemStoresBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private class StoreDiffCallback : DiffUtil.ItemCallback<Store>() {

        override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
            return oldItem == newItem
        }
    }
}