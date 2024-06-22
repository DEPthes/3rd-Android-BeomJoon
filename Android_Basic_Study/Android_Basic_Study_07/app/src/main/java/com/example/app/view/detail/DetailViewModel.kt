package com.example.app.view.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.data.local.PhotoDao
import com.example.app.data.local.PhotoDaoEntity
import com.example.app.data.local.PhotoDatabase
import com.example.app.data.repository.BookmarkRepositoryImpl
import com.example.app.data.repository.PhotoRepositoryImpl
import com.example.app.domain.model.PhotoDetailEntity
import com.example.app.view.utils.UiState
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val photoDao: PhotoDao = PhotoDatabase.getInstance(application).getPhotoDao()
    private var photoRepositoryImpl = PhotoRepositoryImpl()
    private var bookmarkRepositoryImpl = BookmarkRepositoryImpl(photoDao)

    private val _detailState = MutableLiveData<UiState<PhotoDetailEntity>>(UiState.Loading)
    val detailState: LiveData<UiState<PhotoDetailEntity>> get() = _detailState
    private val _bookmarkState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    val bookmarkState: LiveData<UiState<Boolean>> get() = _bookmarkState


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

    fun isBookmark(photoId: String) {
        viewModelScope.launch {
            try {
                val isBookmarked = bookmarkRepositoryImpl.searchIsBookmark(photoId)
                _bookmarkState.value = UiState.Success(isBookmarked)
            } catch (e: Exception) {
                e.printStackTrace()
                _bookmarkState.value = UiState.Failure(e.message)
            }
        }
    }

    fun addBookmark(photoId: String, thumb: String) {
        viewModelScope.launch {
            try {
                val photo = PhotoDaoEntity(photoId, thumb)
                bookmarkRepositoryImpl.addBookmark(photo)
                _bookmarkState.value = UiState.Success(true)
            } catch (e: Exception) {
                e.printStackTrace()
                _detailState.value = UiState.Failure(e.message)
            }
        }
    }

    fun deleteBookmark(photoId: String) {
        viewModelScope.launch {
            try {
                bookmarkRepositoryImpl.deleteBookmark(photoId)
                _bookmarkState.value = UiState.Success(false)
            } catch (e: Exception) {
                e.printStackTrace()
                _detailState.value = UiState.Failure(e.message)
            }
        }
    }
}