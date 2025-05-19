package com.example.lib_with_db.presentation.view_model


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_with_db.domain.usecase.AddItemUseCase
import com.example.lib_with_db.domain.usecase.CheckItemExistsUseCase
import com.example.lib_with_db.domain.usecase.GetItemsUseCase
import com.example.lib_with_db.domain.usecase.LoadMoreItemsUseCase
import com.example.lib_with_db.domain.usecase.ManageSortPreferenceUseCase
import com.example.lib_with_db.domain.usecase.SearchBooksUseCase
import com.example.lib_with_db.presentation.ui_mapper.UIMapper.toModel
import com.example.lib_with_db.presentation.ui_mapper.UIMapper.toUI
import com.example.lib_with_db.presentation.ui_model.BookUI
import com.example.lib_with_db.presentation.ui_model.ItemUI
import com.example.lib_with_db.presentation.ui_model.SortType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class ItemViewModel @Inject constructor (
    private val getItemsUseCase: GetItemsUseCase,
    private val addItemUseCase: AddItemUseCase,
    private val loadMoreItemsUseCase: LoadMoreItemsUseCase,
    private val checkItemExistsUseCase: CheckItemExistsUseCase,
    private val manageSortPreference: ManageSortPreferenceUseCase,
    private val searchBooksUseCase: SearchBooksUseCase,
) : ViewModel() {


    private val _items = MutableLiveData<List<ItemUI>>()
    val items: LiveData<List<ItemUI>> = _items

    private val _selectedItem = MutableLiveData<ItemUI?>()
    val selectedItem: LiveData<ItemUI?> = _selectedItem

    private val _scrollPosition = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> = _scrollPosition

    private val _scrollToLast = MutableLiveData(false)
    val scrollToLast: LiveData<Boolean> = _scrollToLast

    private val _isLoading = MutableStateFlow<Boolean>(false)
    var isLoading: MutableStateFlow<Boolean> = _isLoading

    private val _errorEvent = MutableLiveData<String?>()
    val errorEvent: LiveData<String?> = _errorEvent
    private val _sortType = MutableStateFlow<SortType>(SortType.BY_NAME)
    private var initialLoadCount = 30
    private var pageSize = initialLoadCount / 2
    private var threshold = 10
    private var currentOffset = 0
    private var isForwardPagination = true
    private var totalItemsInDb = 0
    private var searchJob: Job? = null
    private var operationCount: Int = 0

    var currentlyEditingItem: ItemUI? = null
    var isInEditMode: Boolean = false


    init {
        viewModelScope.launch {
            val savedSortType = manageSortPreference.getSortPreference()
            _sortType.value = savedSortType
            loadInitialItems()
        }
    }

    private suspend fun loadInitialItems() = withContext(Dispatchers.IO) {

        _isLoading.value = true

        val items =
            getItemsUseCase(_sortType.value, 0, initialLoadCount).map { item -> item.toUI() }
        _items.value = items
        currentOffset = items.size
        _isLoading.value = false
    }
    fun getSortType(): SortType {
        return _sortType.value
    }

    fun setSortType(type: SortType) {
        viewModelScope.launch {
            manageSortPreference.saveSortPreference(type)
            _sortType.value = type
            refreshData()
        }
    }


    private fun refreshData() {
        viewModelScope.launch {
            _isLoading.value = true
            currentOffset = 0
            val sortType = _sortType.value
            val domainItems = getItemsUseCase(sortType, 0, initialLoadCount)
            _items.value = domainItems.map { item -> item.toUI() }
            currentOffset = domainItems.size
            _isLoading.value = false
        }
    }

    suspend fun loadItems() = withContext(Dispatchers.IO) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(Random.nextLong(from = 100, until = 2001))
            val domainItems = getItemsUseCase(_sortType.value, 0, initialLoadCount)
            _items.value = domainItems.map { item -> item.toUI() }
            currentOffset = domainItems.size
            _isLoading.value = false
        }
    }

    fun loadMoreItems(isForward: Boolean) {
        viewModelScope.launch {

            val currentList = _items.value ?: emptyList()
            val result = loadMoreItemsUseCase(
                currentOffset = currentOffset,
                isForwardPagination = isForward,
                pageSize = pageSize,
                initialLoadCount = initialLoadCount,
                currentItemsCount = _items.value?.size ?: 0
            ) ?: return@launch

            val uiItems = result.newItems.map { item -> item.toUI() }

            val newList = when {
                result.shouldClearFromStart -> {
                    val itemsToKeep = if (result.itemsToRemove > 0) {
                        currentList.drop(result.itemsToRemove)
                    } else {
                        currentList
                    }
                    itemsToKeep + uiItems
                }

                else -> {
                    val itemsToKeep = if (result.itemsToRemove > 0) {
                        currentList.dropLast(result.itemsToRemove)
                    } else {
                        currentList
                    }
                    uiItems + itemsToKeep
                }
            }

            _items.value = newList
            currentOffset = result.updatedOffset
        }
    }

    fun selectItem(item: ItemUI) {
        _selectedItem.value = item
    }

    suspend fun addItem(item: ItemUI) = withContext(Dispatchers.IO) {
        _isLoading.value = true
        addItemUseCase(item)

        val currentItems = _items.value?.toMutableList() ?: mutableListOf()
        currentItems.add(item)
        _items.value = currentItems
        _scrollPosition.value = currentItems.size - 1
        _scrollToLast.value = true
        _isLoading.value = false

    }


    fun resetScrollFlag() {
        _scrollToLast.value = false
    }

    fun setScrollPos(position: Int) {
        _scrollPosition.value = position
    }

    fun clearItems() {
        _items.value = emptyList()
    }

    fun searchBook(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = searchBooksUseCase(query)
                _items.value = result
            } catch (e: Exception) {
                _errorEvent.postValue("Ошибка поиска")
                _items.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun handleLongClick(item: ItemUI) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!checkItemExistsUseCase(item.toModel()) && item is BookUI) {
                addItemUseCase(item)
            }
        }
    }
    fun clearError(){
        _errorEvent.value = null
    }

}
