package br.com.tramalho.mlchallenge.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tramalho.mlchallenge.data.entity.Picture
import br.com.tramalho.mlchallenge.databinding.DetailImgLayoutBinding


class PictureAdapter : ListAdapter<Picture, PictureAdapter.ItemViewHandler>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHandler {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemBinding = DetailImgLayoutBinding.inflate(layoutInflater)

        return ItemViewHandler(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHandler, position: Int) = holder.bind(getItem(position))

    inner class ItemViewHandler(private val binding: DetailImgLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Picture) = with(binding) {

            imgUrl = item.url

            executePendingBindings()
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Picture>() {

        override fun areItemsTheSame(
            oldItem: Picture,
            newItem: Picture
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: Picture,
            newItem: Picture
        ): Boolean {
            return oldItem.url == newItem.url
        }
    }
}