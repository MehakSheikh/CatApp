package com.example.catapp.domain

data class BreedDomain(
    val healthIssues: Int,
    val id: String,
    val lifeSpan: String,
    val name: String,
    val origin: String,
    val sheddingLevel: Int,
    val temperament: String,
)