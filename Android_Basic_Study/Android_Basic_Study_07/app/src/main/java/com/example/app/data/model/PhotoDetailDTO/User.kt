package com.example.app.data.model.PhotoDetailDTO


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("bio")
    val bio: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: LinksX,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("portfolio_url")
    val portfolioUrl: String,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String
)