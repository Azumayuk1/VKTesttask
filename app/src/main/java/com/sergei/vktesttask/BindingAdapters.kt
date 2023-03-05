package com.sergei.vktesttask

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sergei.vktesttask.network.GiphyGifData

// Получает сжатую версию гиф для Grid
@BindingAdapter("gifUrl")
fun bindGif(imgView: ImageView, gifUrl: String?) {
    Glide.with(imgView)
        .load(gifUrl)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.image_load_error)
        .transition(DrawableTransitionOptions.withCrossFade())
        .override(1000, 400)
        .transform(FitCenter(), RoundedCorners(20))
        .into(imgView)
}

// Получает оригинал гифки для
// для GifDetailFragment
@BindingAdapter("gifOrigUrl")
fun bindOrigGif(imgView: ImageView, gifUrl: String?) {
    Glide.with(imgView)
        .load(gifUrl)
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.image_load_error)
        .transition(DrawableTransitionOptions.withCrossFade())
        .override(1000, 1000)
        .fitCenter()
        .transform(FitCenter(), RoundedCorners(20))
        .into(imgView)
}

@BindingAdapter("gifTitle")
fun bindGifTitle(textView: TextView, gifTitle: String?) {
    textView.text = gifTitle
}

@BindingAdapter("gifId")
fun bindGifId(textView: TextView, gifId: String?) {
    textView.text = gifId
}

@BindingAdapter("gifsListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GiphyGifData>?) {
    val adapter = recyclerView.adapter as GiphyGridAdapter
    if (data != null) {
        adapter.submitList(data)
    }
}
