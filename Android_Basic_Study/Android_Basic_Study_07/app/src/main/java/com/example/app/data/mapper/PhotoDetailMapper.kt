package com.example.app.data.mapper

import com.example.app.data.model.PhotoDetailDTO.ResponseDetailDTO
import com.example.app.domain.model.PhotoDetailEntity

object PhotoDetailMapper {
    fun mapperToResponseEntity(item: ResponseDetailDTO): PhotoDetailEntity {
        return item.run {
            val tags = this.tags.map { it.title } // tags에서 title을 추출하여 리스트로 변환

            PhotoDetailEntity(
                id = this.id,
                thumb = this.urls.thumb,
                description = this.description ?: "",
                isBookmark = false,
                tags = tags,
                country = this.location.country ?: "",
                city = this.location.city ?: "",
                likes = this.likes,
                downloads = this.downloads
            )
        }
    }
}