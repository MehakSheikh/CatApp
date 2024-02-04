package com.example.catapp.data.remote

import com.example.catapp.model.BreedDto

data class CatBreedsDto(
    val breedDto: List<BreedDto> ?= null,
    val height: Int ?= null,
    val id: String ?= null,
    val url: String ?= null,
    val width: Int ?= null
)
