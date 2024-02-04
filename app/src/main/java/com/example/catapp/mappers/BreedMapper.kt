package com.example.catapp.mappers

import com.example.catapp.model.BreedDomain
import com.example.catapp.model.BreedDto

fun BreedDto.toDomain() = BreedDomain(
    healthIssues = this.health_issues ?: 0,
    id = this.id.orEmpty(),
    lifeSpan = this.life_span.orEmpty(),
    name = this.name.orEmpty(),
    origin = this.origin.orEmpty(),
    sheddingLevel = this.shedding_level ?: 0,
    temperament = this.temperament.orEmpty()
)
