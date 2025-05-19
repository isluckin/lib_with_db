package com.example.lib_with_db.presentation.adapter


import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lib_with_db.R
import com.example.lib_with_db.databinding.LibraryItemBinding
import com.example.lib_with_db.presentation.ui_model.BookUI
import com.example.lib_with_db.presentation.ui_model.ItemUI


class ItemViewHolder(
    private val binding: LibraryItemBinding,
    private val onClick: (ItemUI) -> Unit,
    private val onLongClick: (ItemUI) -> Unit,
    private val context: Context?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemUI) {
        with(binding) {

            itemName.text = item.itemName
            itemId.text = item.itemId.toString()

            when {
                item is BookUI && item.imageUrl?.isNotEmpty() == true -> {
                    Glide.with(itemView).load(item.imageUrl).placeholder(R.drawable.book_image)
                        .error(R.drawable.item_image).transform(
                            RoundedCorners(15)
                        ).into(binding.itemImage)
                }

                else -> {
                    binding.itemImage.setImageResource(item.imageRes ?: R.drawable.item_image)
                }
            }
            root.setOnClickListener {
                onClick(item)
            }
            root.setOnLongClickListener {
                onLongClick(item)
                Toast.makeText(context, "Добавдено в Библиотеку", Toast.LENGTH_LONG).show()

                true
            }


        }

    }

    fun clearImage() {
        Glide.with(itemView).clear(binding.itemImage)
    }
}
