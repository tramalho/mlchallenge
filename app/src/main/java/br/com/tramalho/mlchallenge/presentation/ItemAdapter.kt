package br.com.tramalho.mlchallenge.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tramalho.mlchallenge.R
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.databinding.ItemRowBinding


class ItemAdapter(private val onItemClickAction: (ItemSearch) -> Unit) :
    ListAdapter<ItemSearch, ItemAdapter.ItemViewHandler>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHandler {

        val layoutInflater = LayoutInflater.from(parent.context)

        val itemBinding = ItemRowBinding.inflate(layoutInflater)

        return ItemViewHandler(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHandler, position: Int) = holder.bind(getItem(position))

    inner class ItemViewHandler(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemSearch) = with(binding) {

            installmentAmount = defineValue(root, item)

            description = item.title

            imgUrl = item.thumbnail

            root.setOnClickListener { onItemClickAction(item) }
            executePendingBindings()
        }

        /**
         * Tratamento para os casos onde nao existe parcelamento
         */
        private fun defineValue(root: View, item: ItemSearch): String {

            item.installments?.let {
                return root.context.getString(R.string.installlments_template,
                    it.quantity.toString(), formatCurrency(it.amount))
            }

            return formatCurrency(item.price).toString()
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<ItemSearch>() {

        override fun areItemsTheSame(
            oldItem: ItemSearch,
            newItem: ItemSearch
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemSearch,
            newItem: ItemSearch
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}