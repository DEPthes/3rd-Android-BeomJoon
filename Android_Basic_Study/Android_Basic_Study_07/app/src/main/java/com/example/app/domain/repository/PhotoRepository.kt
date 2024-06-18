package com.example.app.domain.repository

import com.example.app.domain.model.PhotoEntity

interface PhotoRepository {

    suspend fun getPhotos(): Result<List<PhotoEntity>>
    suspend fun getRandomPhotos(): Result<List<PhotoEntity>>
}