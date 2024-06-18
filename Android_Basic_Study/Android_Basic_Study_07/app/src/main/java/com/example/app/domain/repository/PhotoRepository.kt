package com.example.app.domain.repository

import com.example.app.domain.model.PhotoEntity

interface PhotoRepository {
    suspend fun getRandomPhotos(clientId: String, query: String?): Result<List<PhotoEntity>>
}