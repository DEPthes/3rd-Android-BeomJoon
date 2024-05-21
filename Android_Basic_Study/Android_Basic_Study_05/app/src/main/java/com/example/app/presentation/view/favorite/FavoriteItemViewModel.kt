package com.example.app.presentation.view.favorite

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.db.AppDatabase
import com.example.app.data.db.FavoriteItemEntity
import com.example.app.presentation.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteItemViewModel : ViewModel() {
    private val _uiState = MutableLiveData<UiState<List<FavoriteItemEntity>>>(UiState.Loading)
    val uiState: LiveData<UiState<List<FavoriteItemEntity>>> get() = _uiState

    fun fetchData(context: Context) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val favoriteList =
                AppDatabase.getInstance(context).getFavoriteDAO().getFavoriteItemList()
            if (favoriteList.isEmpty()) {
                launch(Dispatchers.Main) { _uiState.value = UiState.Failure("데이터 불러오기 실패") }
            } else {
                launch(Dispatchers.Main) {_uiState.value = UiState.Success(favoriteList) }
            }
        }
    }
}