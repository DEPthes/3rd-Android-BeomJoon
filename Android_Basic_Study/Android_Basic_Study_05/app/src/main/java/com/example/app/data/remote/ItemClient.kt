package com.example.app.data.remote

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ItemClient {
    private var instance: Retrofit? = null
    private const val TAG = "Log Interceptor"
    private val gson = GsonBuilder().setLenient().create()
    private const val baseUrl = "https://dummyjson.com/"

    fun getInstance(): Retrofit {
        if (instance == null) {
            val client = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                try {
                    Log.d(TAG, message)
                } catch (e: Exception) {
                    Log.d(TAG, message)
                }
            }
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(loggingInterceptor)

            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client.build())
                .build()
        }
        return instance!!
    }
}