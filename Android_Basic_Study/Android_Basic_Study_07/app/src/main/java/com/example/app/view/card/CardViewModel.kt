package com.example.app.view.card

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.repository.PhotoRepositoryImpl
import com.example.app.domain.model.PhotoEntity
import com.example.app.view.utils.UiState
import kotlinx.coroutines.launch

class CardViewModel : ViewModel() {
    private var photoRepositoryImpl = PhotoRepositoryImpl()

    private val _randomState = MutableLiveData<UiState<List<PhotoEntity>>>(UiState.Loading)
    val randomState: LiveData<UiState<List<PhotoEntity>>> get() = _randomState

    fun getRandomPhotos() {
        _randomState.value = UiState.Loading

        viewModelScope.launch {
            try {
                photoRepositoryImpl.getRandomPhotos().onSuccess {
//                    Log.d("TAG", it.toString())
                    _randomState.value = UiState.Success(it)
                }.onFailure {
                    _randomState.value = UiState.Failure(it.message)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _randomState.value = UiState.Failure(e.message)
            }
        }
    }
}