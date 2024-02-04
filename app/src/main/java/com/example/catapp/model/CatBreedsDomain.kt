package com.example.catapp.model

data class CatBreedsDomain(
    val breeds: List<BreedDomain>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)
