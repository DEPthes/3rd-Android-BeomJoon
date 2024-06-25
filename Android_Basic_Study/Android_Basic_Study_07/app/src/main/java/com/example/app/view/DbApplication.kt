package com.example.app.view

import android.app.Application
import com.example.app.data.local.PhotoDao
import com.example.app.data.local.PhotoDatabase
import com.example.app.data.repository.BookmarkRepositoryImpl

class DbApplication : Application() {
    private lateinit var bookmarkRepositoryImpl: BookmarkRepositoryImpl

    companion object {
        private lateinit var photoDao: PhotoDao
        private lateinit var instance: DbApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        val db = PhotoDatabase.getInstance(this)
        photoDao = db.getPhotoDao()

        bookmarkRepositoryImpl = BookmarkRepositoryImpl(photoDao)
    }
}