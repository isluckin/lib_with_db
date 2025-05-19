package com.example.lib_with_db.presentation.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.lib_with_db.databinding.LibraryItemBinding
import com.example.lib_with_db.presentation.ui_model.ItemUI

class ItemAdapter(
    private val onClick: (ItemUI) -> Unit,
    private val onLongClick: (ItemUI) -> Unit,
    private val context: Context?
) : ListAdapter<ItemUI, ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onClick, onLongClick, context)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onViewRecycled(holder: ItemViewHolder) {
        holder.clearImage()
        super.onViewRecycled(holder)
    }

}


class ItemDiffCallback : DiffUtil.ItemCallback<ItemUI>() {
    override fun areItemsTheSame(oldItem: ItemUI, newItem: ItemUI) = oldItem.itemId == newItem.itemId
    override fun areContentsTheSame(oldItem: ItemUI, newItem: ItemUI) = oldItem == newItem
}

