package com.diayan.kaal.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.diayan.kaal.data.model.Event
import com.diayan.kaal.ui.home.EventsAdapter

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}

@BindingAdapter("gridData")
fun bindingRecyclerView(recyclerView: RecyclerView, eventList: PagedList<Event>) {
    val adapter = recyclerView.adapter as EventsAdapter
    adapter.submitList(eventList)
}