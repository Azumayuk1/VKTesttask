package com.sergei.vktesttask.network

import com.squareup.moshi.Json

data class GiphyResponse(
    @Json(name = "data") val data: List<GiphyGifData>,
    //@Json(name = "pagination") val paginationData: String
)

data class GiphyGifData(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "images") val images: ImagesData
)

data class ImagesData(
    @Json(name = "original") val originalImage: OriginalImageData,
    @Json(name = "fixed_height") val fixedHeightImage: FixedHeightImage
)

data class OriginalImageData(
    @Json(name = "url") val gifSourceUrl: String
)

data class FixedHeightImage(
    @Json(name = "url") val gifSourceUrl: String
)



