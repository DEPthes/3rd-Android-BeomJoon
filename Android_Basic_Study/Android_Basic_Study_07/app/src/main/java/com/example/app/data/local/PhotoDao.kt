package com.example.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PhotoDao {
//    @Query("")
//    fun getPhotoDetail(): List<String>    // 북마크 사진 클릭했을 시 상세정보 출력

    @Insert
    fun addBookmark(photoInfo: PhotoDaoEntity)    // 사진 북마크에 추가
}