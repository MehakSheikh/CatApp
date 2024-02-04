package com.example.catapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CatDAO {

    @Upsert
    suspend fun upsertAll(cats: List<CatDataEntity>)

    @Query("SELECT DISTINCT * FROM CatDataEntity")
    fun getAllCatsPaging(): PagingSource<Int, CatDataEntity>

    @Query("DELETE FROM CatDataEntity")
    suspend fun clearAll()

    /*@Query("SELECT * FROM CatDataEntity")
    suspend fun getAllCats(): List<CatDataEntity>*/

    // for loading 10 records at a time
    @Query("SELECT * FROM CatDataEntity ORDER BY id LIMIT :pageSize OFFSET :offset")
    suspend fun getPaginatedCats(pageSize: Int, offset: Int): List<CatDataEntity>

    @Query("SELECT COUNT(*) FROM CatDataEntity")
    suspend fun getTotalSize(): Int
}