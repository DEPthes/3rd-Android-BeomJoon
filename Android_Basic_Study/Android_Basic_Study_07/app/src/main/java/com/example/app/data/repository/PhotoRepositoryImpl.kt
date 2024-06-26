package com.example.app.data.repository

import android.util.Log
import com.example.app.BuildConfig
import com.example.app.data.mapper.PhotoDetailMapper
import com.example.app.data.mapper.PhotoMapper
import com.example.app.data.remote.RetrofitClient
import com.example.app.data.remote.api.PhotoService
import com.example.app.domain.model.PhotoDetailEntity
import com.example.app.domain.model.PhotoEntity
import com.example.app.domain.repository.PhotoRepository
import org.json.JSONObject

class PhotoRepositoryImpl : PhotoRepository {
    private val service = RetrofitClient.getInstance().create(PhotoService::class.java)

    override suspend fun getPhotos(currentPage: Int): Result<List<PhotoEntity>> {
        val res =
            service.getPhotos("v1", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}", currentPage)
        return if (res.isSuccessful) {
            if (res.body() == null) Result.success(listOf())
            else Result.success(PhotoMapper.mapperToResponseEntity(res.body()!!))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }

    override suspend fun getRandomPhotos(): Result<List<PhotoEntity>> {
        val res = service.getRandomPhotos("v1", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
        return if (res.isSuccessful) {
            if (res.body() == null) Result.success(listOf())
            else Result.success(PhotoMapper.mapperToResponseEntity(res.body()!!))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(java.lang.Exception(errorMsg))
        }
    }

    override suspend fun getPhotoDetail(photoId: String): Result<PhotoDetailEntity> {
        val res =
            service.getPhotoDetail("v1", "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}", photoId)
        return if (res.isSuccessful) {
            Log.d("TAG", res.body().toString())
            Result.success(PhotoDetailMapper.mapperToResponseEntity(res.body()!!))
        } else {
            val errorMsg = JSONObject(res.errorBody()!!.string()).getString("msg")
            Result.failure(Exception(errorMsg))
        }
    }
}