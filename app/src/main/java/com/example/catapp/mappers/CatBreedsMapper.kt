package com.example.catapp.mappers

import com.example.catapp.model.CatBreedsDomain
import com.example.catapp.model.CatBreedsDto

fun CatBreedsDto.toDomain() = CatBreedsDomain(
    breeds = this.breedDto?.map { it.toDomain() } ?: emptyList(),
    height = this.height ?: 0,
    id = this.id.orEmpty(),
    url = this.url.orEmpty(),
    width = this.width ?: 0
)