package com.sergei.vktesttask

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sergei.vktesttask.network.GiphyGifData

@BindingAdapter("gifUrl")
fun bindGif(imgView: ImageView, gifUrl: String?) {
    Glide.with(imgView)
        .load(gifUrl)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.image_load_error)
        .transition(DrawableTransitionOptions.withCrossFade())
        .dontTransform()
        .override(1000, 400)
        .fitCenter()
        .into(imgView)
}

@BindingAdapter("gifsListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GiphyGifData>?) {
    val adapter = recyclerView.adapter as GiphyGridAdapter
    if (data != null) {
        adapter.submitList(data)
    }
}
