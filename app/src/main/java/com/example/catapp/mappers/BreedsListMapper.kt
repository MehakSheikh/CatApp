package com.example.catapp.mappers

import com.example.catapp.domain.BreedsListDomain
import com.example.catapp.domain.BreedsListDto

fun BreedsListDto.toDomain() = BreedsListDomain(
    breeds = this.breeds?.map { it.toDomain() } ?: emptyList(),
    height = this.height ?: 0,
    width = this.width ?: 0,
    id = this.id.orEmpty(),
    url = this.url.orEmpty(),
)
