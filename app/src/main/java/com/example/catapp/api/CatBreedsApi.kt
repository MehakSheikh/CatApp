package com.example.catapp.api

import com.example.catapp.domain.BreedsListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatBreedsApi {

    @GET("/v1/images/search")
    suspend fun getCatsData(@Query("limit") limit: Int,
                             @Query("has_breeds") has_breeds: Int,
                             @Query("api_key") api_key: String): List<BreedsListDto>

    @GET("/v1/images/search")
    suspend fun getBreeds(@Query("limit") limit: Int,
                          @Query("breed_ids") breed_ids: String,
                          @Query("api_key") api_key: String): List<BreedsListDto>
}