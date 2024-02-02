package com.example.catapp.mappers

import com.example.catapp.domain.WeightDomain
import com.example.catapp.domain.WeightDto

fun WeightDto.toDomain() = WeightDomain(
    imperial = this.imperial.orEmpty(),
    metric = this.metric.orEmpty()
)