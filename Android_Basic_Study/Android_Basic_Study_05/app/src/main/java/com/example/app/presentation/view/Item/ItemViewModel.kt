package com.example.app.presentation.view.Item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.repository.ItemRepositoryImpl
import com.example.app.domain.model.item.ItemEntity
import com.example.app.presentation.utils.UiState
import kotlinx.coroutines.launch

class ItemViewModel : ViewModel() {
    private val itemRepositoryImpl = ItemRepositoryImpl()

    private val _uiState = MutableLiveData<UiState<List<ItemEntity>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<ItemEntity>>> get() = _uiState

    fun fetchData(searchItem: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch {
            itemRepositoryImpl.getItem(searchItem)
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onFailure {
                    _uiState.value = UiState.Failure(it.message)
                }
        }
    }
}