package com.example.catapp.domain

data class BreedsListDto(
    val breeds: List<BreedDto> ?= null,
    val height: Int ?= null,
    val id: String ?= null,
    val url: String ?= null,
    val width: Int ?= null
)