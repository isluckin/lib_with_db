package com.example.lib_with_db.presentation

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.lib_with_db.presentation.view_model.ItemViewModel
import com.example.lib_with_db.presentation.view_model.ItemViewModelFactory
import com.example.lib_with_db.R
import com.example.lib_with_db.databinding.ActivityMainBinding
import com.example.lib_with_db.presentation.ui_model.BookUI
import com.example.lib_with_db.presentation.ui_model.DiskUI
import com.example.lib_with_db.presentation.ui_model.NewspaperUI
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ItemViewModelFactory
    private lateinit var viewModel: ItemViewModel

    private val isLandscape: Boolean
        get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as LibraryApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemViewModel::class.java)

        lifecycleScope.launch {

            setup(savedInstanceState)
        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.addButton.setOnClickListener {
            lifecycleScope.launch {
                showDialog()
            }
        }

        viewModel.selectedItem.observe(this) { item ->
            if (item != null) {
                val detailFragment = DetailFragment.Companion.newInstance(item)
                if (isLandscape) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.detail_container, detailFragment).commit()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.single_container, detailFragment).addToBackStack(null)
                        .commit()
                }
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val listFragment = supportFragmentManager.findFragmentById(R.id.list_container)
        val detailFragment = supportFragmentManager.findFragmentById(R.id.detail_container)

        if (listFragment != null) {
            supportFragmentManager.putFragment(outState, "listFragment", listFragment)
        }
        if (detailFragment != null) {
            supportFragmentManager.putFragment(outState, "detailFragment", detailFragment)
        }
    }

    private fun showDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.menu, null)
        val radioGroup = dialogView.findViewById<RadioGroup>(R.id.radioGroup)

        val dialog = AlertDialog.Builder(this).setTitle("Add new Item").setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val selectedItem = when (radioGroup.checkedRadioButtonId) {
                    R.id.addBook -> BookUI.Companion.createEmptyBook()
                    R.id.addNews -> NewspaperUI.Companion.createEmptyNewspaper()
                    R.id.addDisk -> DiskUI.Companion.createEmptyDisk()
                    else -> null
                }

                selectedItem?.let {
                    viewModel.currentlyEditingItem = it
                    viewModel.isInEditMode = true

                    val detailFragment = DetailFragment.Companion.newInstance(it, isEditMode = true)

                    val containerId =
                        if (isLandscape) R.id.detail_container else R.id.single_container

                    supportFragmentManager.beginTransaction().replace(containerId, detailFragment)
                        .addToBackStack(null).commit()
                }
            }.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.create()

        dialog.show()
    }

    private fun setup(state: Bundle?) {
        if (viewModel.isInEditMode && viewModel.currentlyEditingItem != null) {
            val detailFragment = DetailFragment.Companion.newInstance(
                viewModel.currentlyEditingItem, isEditMode = true
            )
            if (isLandscape) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.list_container, ListFragment()).commit()

            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.single_container, detailFragment).addToBackStack(null).commit()
            }
        }
        if (state == null) {
            if (isLandscape) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.list_container, ListFragment())
                    .replace(R.id.detail_container, DetailFragment.Companion.newInstance(null))
                    .commit()
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.single_container, ListFragment()).commit()
            }
        } else {
            if (isLandscape) {
                val listFragment = supportFragmentManager.findFragmentByTag("listFragment")
                val detailFragment = supportFragmentManager.findFragmentByTag("detailFragment")

                if (listFragment == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.list_container, ListFragment(), "listFragment").commit()
                }

                if (detailFragment == null) {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.detail_container,
                        DetailFragment.Companion.newInstance(null),
                        "detailFragment"
                    ).commit()
                }
            } else {
                val listFragment = supportFragmentManager.findFragmentByTag("listFragment")

                if (listFragment == null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.single_container, ListFragment(), "listFragment").commit()
                }
            }
        }

    }
}