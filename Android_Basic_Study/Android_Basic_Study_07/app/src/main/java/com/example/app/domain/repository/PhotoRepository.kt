package com.example.app.domain.repository

import com.example.app.domain.model.PhotoDetailEntity
import com.example.app.domain.model.PhotoEntity

interface PhotoRepository {

    suspend fun getPhotos(currentPage: Int): Result<List<PhotoEntity>>
    suspend fun getRandomPhotos(): Result<List<PhotoEntity>>
    suspend fun getPhotoDetail(photoId: String): Result<PhotoDetailEntity>
}