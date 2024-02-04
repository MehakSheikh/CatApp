package com.example.catapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatDataEntity(
    @PrimaryKey()
    val id: String,
    val healthIssues: Int?,
    val lifeSpan: String?,
    val name: String?,
    val origin: String?,
    val sheddingLevel: Int?,
    val temperament: String?,
    val imageUrl:String?
)
