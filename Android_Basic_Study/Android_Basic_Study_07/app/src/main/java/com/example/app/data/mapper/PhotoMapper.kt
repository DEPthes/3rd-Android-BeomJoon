package com.example.app.data.mapper

import com.example.app.data.model.ResponsePhotoDTOItem
import com.example.app.domain.model.PhotoEntity

object PhotoMapper {

    fun mapperToResponseEntity(item: List<ResponsePhotoDTOItem>): List<PhotoEntity> {
        val photoList = mutableListOf<PhotoEntity>()
        item.forEach {
            val id = it.id
            val thumbUrl = it.urls.thumb
            val description = it.description ?: ""

            photoList.add(PhotoEntity(id, thumbUrl, description))
        }
        return photoList
    }
}