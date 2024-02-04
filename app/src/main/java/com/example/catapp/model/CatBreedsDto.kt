package com.example.catapp.model

data class CatBreedsDto(
    val breedDto: List<BreedDto> ?= null,
    val height: Int ?= null,
    val id: String ?= null,
    val url: String ?= null,
    val width: Int ?= null
)
