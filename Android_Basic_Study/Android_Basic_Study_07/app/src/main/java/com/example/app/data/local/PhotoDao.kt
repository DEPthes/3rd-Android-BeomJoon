package com.example.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("select * from bookmarkPhoto")
    fun getBookmarkList(): List<PhotoDaoEntity>     // 북마크 사진들 출력

    @Insert
    fun addBookmark(photoInfo: PhotoDaoEntity)    // 사진 북마크에 추가

    @Delete
    fun deleteBookmark(photoInfo: PhotoDaoEntity)   // 사진 북마크에서 제거

    @Query("DELETE FROM bookmarkPhoto")
    suspend fun deleteAllBookmarks()
}