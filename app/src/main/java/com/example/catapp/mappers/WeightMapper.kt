package com.example.catapp.mappers

import com.example.catapp.model.WeightDomain
import com.example.catapp.model.WeightDto

fun WeightDto.toDomain() = WeightDomain(
    imperial = this.imperial.orEmpty(),
    metric = this.metric.orEmpty()
)