package com.example.catapp.domain

import com.example.catapp.model.Breed

data class CatBreedsDto(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
