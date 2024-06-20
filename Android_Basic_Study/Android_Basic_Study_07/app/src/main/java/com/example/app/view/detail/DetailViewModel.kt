package com.example.app.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.repository.PhotoRepositoryImpl
import com.example.app.domain.model.PhotoDetailEntity
import com.example.app.view.utils.UiState
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private var photoRepositoryImpl = PhotoRepositoryImpl()

    private val _detailState = MutableLiveData<UiState<PhotoDetailEntity>>(UiState.Loading)
    val detailState: LiveData<UiState<PhotoDetailEntity>> get() = _detailState

    fun fetchData(photoId: String) {
        _detailState.value = UiState.Loading

        viewModelScope.launch {
            photoRepositoryImpl.getPhotoDetail(photoId).onSuccess {
                Log.d("DetailViewModel", it.toString())
                _detailState.value = UiState.Success(it)
            }.onFailure {
                _detailState.value = UiState.Failure(it.message)
            }
        }
    }
}