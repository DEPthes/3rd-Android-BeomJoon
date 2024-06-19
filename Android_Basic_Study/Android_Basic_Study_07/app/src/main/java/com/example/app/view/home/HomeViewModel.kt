package com.example.app.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.repository.PhotoRepositoryImpl
import com.example.app.domain.model.PhotoEntity
import com.example.app.view.utils.UiState
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private var photoRepositoryImpl = PhotoRepositoryImpl()

    private val _photoState = MutableLiveData<UiState<List<PhotoEntity>>>(UiState.Loading)
    val photoState: LiveData<UiState<List<PhotoEntity>>> get() = _photoState

    fun getPhotos() {
        _photoState.value = UiState.Loading

        viewModelScope.launch {
            try {
                photoRepositoryImpl.getPhotos().onSuccess {
                    _photoState.value = UiState.Success(it)
                }.onFailure {
                    _photoState.value = UiState.Failure(it.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _photoState.value = UiState.Failure(e.message)
            }
        }
    }
}