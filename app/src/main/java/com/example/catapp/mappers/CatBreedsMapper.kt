package com.example.catapp.mappers

import com.example.catapp.domain.CatBreedsDomain
import com.example.catapp.domain.CatBreedsDto

fun CatBreedsDto.toDomain() = CatBreedsDomain(
    breeds = this.breedDto?.map { it.toDomain() } ?: emptyList(),
    height = this.height ?: 0,
    id = this.id.orEmpty(),
    url = this.url.orEmpty(),
    width = this.width ?: 0
)