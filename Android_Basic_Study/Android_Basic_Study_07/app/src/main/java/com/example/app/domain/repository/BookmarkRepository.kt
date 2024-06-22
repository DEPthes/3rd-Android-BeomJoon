package com.example.app.domain.repository

import com.example.app.data.local.PhotoDaoEntity

interface BookmarkRepository {
    suspend fun getBookmarkPhotos(): List<PhotoDaoEntity>
    suspend fun searchIsBookmark(photoId: String): Boolean
    suspend fun addBookmark(photoInfo: PhotoDaoEntity)
    suspend fun deleteBookmark(photoId: String)
    suspend fun deleteAllBookmarks()
}