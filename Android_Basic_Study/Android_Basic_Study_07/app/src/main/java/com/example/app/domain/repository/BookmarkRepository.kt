package com.example.app.domain.repository

import com.example.app.data.local.PhotoDaoEntity

interface BookmarkRepository {
    suspend fun getBookmarkPhotos(): List<PhotoDaoEntity>
    suspend fun addBookmark(photoInfo: PhotoDaoEntity)
    suspend fun removeBookmark(photoInfo: PhotoDaoEntity)
    suspend fun deleteAllBookmarks()
}