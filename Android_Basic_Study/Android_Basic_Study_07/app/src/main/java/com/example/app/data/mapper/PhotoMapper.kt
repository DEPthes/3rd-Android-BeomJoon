package com.example.app.data.mapper

import com.example.app.data.model.ResponsePhotoDTOItem
import com.example.app.domain.model.PhotoEntity

object PhotoMapper {

    fun mapperToResponseEntity(item: List<ResponsePhotoDTOItem>): List<PhotoEntity> {
        val photoList = mutableListOf<PhotoEntity>()
        item.map {
            it.urls.thumb.let { thumbUrl -> photoList.add(PhotoEntity(thumbUrl)) }
        }
        return photoList
    }
}