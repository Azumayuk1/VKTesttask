package com.sergei.vktesttask


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sergei.vktesttask.databinding.GridViewItemBinding
import com.sergei.vktesttask.network.GiphyGifData

class GiphyGridAdapter :
    ListAdapter<GiphyGifData, GiphyGridAdapter.GifViewHolder>(
        DiffCallback
    ) {

    class GifViewHolder(private var binding: GridViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gif: GiphyGifData) {
            binding.gif = gif
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        return GifViewHolder(GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gif = getItem(position)
        holder.bind(gif)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GiphyGifData>() {
        override fun areItemsTheSame(oldItem: GiphyGifData, newItem: GiphyGifData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GiphyGifData, newItem: GiphyGifData): Boolean {
            return oldItem.images.originalImage.gifSourceUrl == newItem.images.originalImage.gifSourceUrl
        }

    }
}