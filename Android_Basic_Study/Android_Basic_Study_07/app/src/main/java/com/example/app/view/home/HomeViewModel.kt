package com.example.app.view.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.app.data.local.PhotoDao
import com.example.app.data.local.PhotoDaoEntity
import com.example.app.data.local.PhotoDatabase
import com.example.app.data.repository.BookmarkRepositoryImpl
import com.example.app.data.repository.PhotoRepositoryImpl
import com.example.app.domain.model.PhotoEntity
import com.example.app.view.utils.UiState
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val photoDao: PhotoDao = PhotoDatabase.getInstance(application).getPhotoDao()
    private var photoRepositoryImpl = PhotoRepositoryImpl()
    private var bookmarkRepositoryImpl = BookmarkRepositoryImpl(photoDao)

    private val _photoState = MutableLiveData<UiState<List<PhotoEntity>>>(UiState.Loading)
    val photoState: LiveData<UiState<List<PhotoEntity>>> get() = _photoState
    private val _bookmarkState = MutableLiveData<UiState<List<PhotoDaoEntity>>>(UiState.Loading)
    val bookmarkState: LiveData<UiState<List<PhotoDaoEntity>>> get() = _bookmarkState

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

    fun getBookmarkPhotos() {
        _bookmarkState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val it = bookmarkRepositoryImpl.getBookmarkPhotos()
                _bookmarkState.value = UiState.Success(it)
            } catch (e: Exception) {
                e.printStackTrace()
                _bookmarkState.value = UiState.Failure(e.message)
            }
        }
    }

    fun addBookmark(photoInfo: PhotoDaoEntity) {
        viewModelScope.launch {
            try {
                bookmarkRepositoryImpl.addBookmark(photoInfo)
                // 추가한 후 다시 북마크 목록을 로드할 수 있도록 갱신
                getBookmarkPhotos()
            } catch (e: Exception) {
                e.printStackTrace()
                _bookmarkState.value = UiState.Failure(e.message)
            }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch {
            try {
                bookmarkRepositoryImpl.deleteAllBookmarks()
                getBookmarkPhotos()
            } catch (e: Exception) {
                e.printStackTrace()
                _bookmarkState.value = UiState.Failure(e.message)
            }
        }
    }
}