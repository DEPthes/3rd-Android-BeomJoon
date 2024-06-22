package com.example.app.data.repository

import com.example.app.data.local.PhotoDao
import com.example.app.data.local.PhotoDaoEntity
import com.example.app.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarkRepositoryImpl(private val photoDao: PhotoDao) : BookmarkRepository {

    override suspend fun getBookmarkPhotos(): List<PhotoDaoEntity> = withContext(Dispatchers.IO) {
        photoDao.getBookmarkList()
    }

    override suspend fun searchIsBookmark(photoId: String): Boolean = withContext(Dispatchers.IO) {
        photoDao.searchIsBookmark(photoId)
    }

    override suspend fun addBookmark(photoInfo: PhotoDaoEntity) {
        withContext(Dispatchers.IO) {
            photoDao.addBookmark(photoInfo)
        }
    }

    override suspend fun deleteBookmark(photoId: String) {
        withContext(Dispatchers.IO) {
            photoDao.deleteBookmark(photoId)
        }
    }

    override suspend fun deleteAllBookmarks() {
        photoDao.deleteAllBookmarks()
    }
}