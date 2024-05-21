package com.example.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM FavoriteItem")
    fun getFavoriteItemList(): List<FavoriteItemEntity>  // 찜한 아이템 조회

    @Insert
    fun insertItem(itemInfo: FavoriteItemEntity)    // 찜한 아이템 db에 insert

    @Query("DELETE FROM FavoriteItem WHERE id = :id")
    fun deleteItem(id: Int)    // 찜 목록 삭제
}