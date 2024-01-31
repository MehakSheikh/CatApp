package com.example.catapp.api

import com.example.catapp.model.Breed
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface CatBreedsApi {

    //    @GET("/v1/images/search?")
//    @Headers("limit : 12","has_breeds : 1","api_key : live_6mPO3thEhQQp5lLKHJzvSD4tyrPCiRg28D3VkxiURvMclrSV5AzIckEuIvCzSNce")
    @GET("images/search?limit=3&has_breeds=1&api_key=live_6mPO3thEhQQp5lLKHJzvSD4tyrPCiRg28D3VkxiURvMclrSV5AzIckEuIvCzSNce")
    suspend fun getCatBreeds(): Call<List<Breed>>

//    @GET("/v1/images/search?")
//    suspend fun getBreeds(@Header("breed_id") breedId: String)
}