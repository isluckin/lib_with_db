package com.example.lib_with_db.presentation


import android.app.AlertDialog
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lib_with_db.presentation.view_model.ItemViewModel
import com.example.lib_with_db.R
import com.example.lib_with_db.databinding.FragmentDetailBinding
import com.example.lib_with_db.presentation.ui_mapper.UIMapper.toModel
import com.example.lib_with_db.presentation.ui_model.BookUI
import com.example.lib_with_db.presentation.ui_model.DiskUI
import com.example.lib_with_db.presentation.ui_model.ItemUI
import com.example.lib_with_db.presentation.ui_model.NewspaperUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var isEditMode = false
    private val viewModel: ItemViewModel by activityViewModels()
    private var item: ItemUI? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getParcelable(ITEM_KEY)
        isEditMode = arguments?.getBoolean(EDIT_MODE_KEY) == true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorEvent.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                AlertDialog.Builder(requireContext()).setTitle("Error").setMessage(it)
                    .setPositiveButton("OK") { _, _ ->
                        viewModel.clearError()
                    }.show()
            }
        }
        setupStartFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupStartFragment() {
        if (item == null && !isEditMode) {
            binding.root.visibility = View.GONE
            return
        }
        if (isEditMode) {
            setupEditableFields()
            binding.saveButton.visibility = View.VISIBLE
            binding.saveButton.setOnClickListener {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        viewModel.addItem(createItemFromInputs())
                    }
                    if (viewModel.errorEvent.value == null) {
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        } else {
            binding.saveButton.visibility = View.GONE
            fillItemData()
        }

        fillItemData()
    }

    private fun setupEditableFields() {
        with(binding) {
            bigItemName.isEnabled = true
            bigItemID.isEnabled = true
            bookAuthor.isEnabled = true
            bookPages.isEnabled = true
            newspaperNumber.isEnabled = true
            newspaperMonth.isEnabled = true
            diskType.isEnabled = true

            when (item) {
                is BookUI -> binding.bookGroup.visibility = View.VISIBLE
                is NewspaperUI -> binding.newspaperGroup.visibility = View.VISIBLE
                is DiskUI -> binding.diskGroup.visibility = View.VISIBLE
                else -> {
                    binding.bookGroup.visibility = View.VISIBLE
                    binding.newspaperGroup.visibility = View.VISIBLE
                    binding.diskGroup.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun fillItemData() {
        item?.let { item ->
            when (item) {
                is BookUI -> {
                    if (item.imageUrl != "") Glide.with(requireContext()).load(item.imageUrl)
                        .placeholder(R.drawable.book_image).into(binding.bigItemImage)
                    else binding.bigItemImage.setImageResource(item.imageRes?: R.drawable.item_image)

                }

                else -> binding.bigItemImage.setImageResource(item.imageRes ?: R.drawable.item_image)
            }
            binding.bigItemName.setText(item.itemName)
            binding.bigItemID.setText(item.itemId.toString())

            when (item) {
                is BookUI -> {
                    binding.bookGroup.visibility = View.VISIBLE
                    binding.bookAuthor.setText(item.bookAuthor)
                    binding.bookPages.setText(item.bookPages.toString())
                }

                is NewspaperUI -> {
                    binding.newspaperGroup.visibility = View.VISIBLE
                    binding.newspaperNumber.setText(item.newspaperNumber.toString())
                    binding.newspaperMonth.setText(item.month)
                }

                is DiskUI -> {
                    binding.diskGroup.visibility = View.VISIBLE
                    binding.diskType.setText(item.diskType)
                }
            }
        }
    }

    private fun createItemFromInputs(): ItemUI {
        val name = binding.bigItemName.text.toString()
        val id = binding.bigItemID.text.toString().toIntOrNull() ?: 0

        return when (item) {
            is BookUI -> BookUI(
                itemId = id.toString(),
                itemName = name,
                bookAuthor = binding.bookAuthor.text.toString(),
                bookPages = binding.bookPages.text.toString().toInt(),
                isAvailable = true,
                imageRes = R.drawable.book_image,
                imageUrl = ""
            )

            is NewspaperUI -> NewspaperUI(
                itemId = id.toString(),
                itemName = name,
                newspaperNumber = binding.newspaperNumber.text.toString().toInt(),
                month = binding.newspaperMonth.text.toString(),
                isAvailable = true,
                imageRes = R.drawable.newspaper_image
            )

            is DiskUI -> DiskUI(
                itemName = name,
                itemId = id.toString(),
                imageRes = R.drawable.disk_image,
                diskType = binding.diskType.text.toString(),
                isAvailable = true
            )

            else -> TODO()
        }
    }

    companion object {
        private const val ITEM_KEY = "item_key"
        private const val EDIT_MODE_KEY = "edit_mode"

        fun newInstance(item: ItemUI?, isEditMode: Boolean = false): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle().apply {
                putParcelable(ITEM_KEY, item as Parcelable?)
                putBoolean(EDIT_MODE_KEY, isEditMode)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
