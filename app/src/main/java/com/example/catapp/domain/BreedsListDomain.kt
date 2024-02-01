package com.example.catapp.domain

data class BreedsListDomain(
    val breeds: List<BreedDomain>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)