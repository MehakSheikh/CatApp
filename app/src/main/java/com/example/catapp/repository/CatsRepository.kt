package com.example.catapp.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.catapp.data.remote.CatBreedsApi
import com.example.catapp.constants.Constant
import com.example.catapp.model.BreedsListDto
import com.example.catapp.data.local.CatDAO
import com.example.catapp.data.local.CatDataEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CatsRepository @Inject constructor(private val catApiService: CatBreedsApi, private val catDao: CatDAO) {

    private val _breedData = MutableStateFlow<List<BreedsListDto>>(emptyList())
    val breedData: StateFlow<List<BreedsListDto>>
        get() = _breedData

    suspend fun getCatsData(pageNumber:Int) : List<BreedsListDto>{
        val catlist = catApiService.getCatsData(
            10,
            1,
            pageNumber,
            Constant.apiKey
        )
        Log.d("mehak testing",catlist.toString())

        mapResponseToEntities(catlist)
        logAllCats()

        return catApiService.getCatsData(
            10,
            1,
            1,
            Constant.apiKey
        )

    }

    suspend fun getSelectedBreedDetails(breedId: String) {
        val breedList = catApiService.getBreeds(
            50,
            breedId,
            Constant.apiKey
        )

        Log.d("breed list",breedList.toString())
        if (breedList.isEmpty() != null)
            _breedData.emit(breedList)



    }

    suspend fun mapResponseToEntities(breedList: List<BreedsListDto>) {
        val catEntities = mutableListOf<CatDataEntity>()

        for (breed in breedList) {
            Log.d("breed testing", breed.toString())

            val catEntity = CatDataEntity(
                id = breed.breeds!![0].id!!,
                healthIssues = breed.breeds[0].health_issues,
                lifeSpan = breed.breeds[0].life_span,
                name = breed.breeds[0].name,
                origin = breed.breeds[0].origin,
                sheddingLevel = breed.breeds[0].shedding_level,
                temperament = breed.breeds[0].temperament,
                imageUrl = breed.url
            )

            catEntities.add(catEntity)
        }
        try {
            catDao.upsertAll(catEntities)
            Log.d("Mehak log cat", "Data inserted into Room successfully.")
        } catch (e: Exception) {
            Log.e("Mehak log cat", "Error inserting data into Room: ${e.message}", e)
        }
    }

    fun getPagingData(): Flow<PagingData<CatDataEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { catDao.getAllCatsPaging() }
        ).flow
    }


    ////this function is only for testing purposes'breedList.isEmpty() != null
    suspend fun logAllCats() {
        Log.d("mehak log ","testing 3")
        try {
            val catsList: List<CatDataEntity> = catDao.getAllCats() // Replace with the actual function to get all cats

            for (cat in catsList) {
                // Log each cat's information
                Log.d("CatInfo from repo Mehak", "ID: ${cat.id}, Name: ${cat.name}, Origin: ${cat.origin}, Life Span: ${cat.lifeSpan}")
            }
        } catch (e: Exception) {
            Log.e("CatInfo", "Error logging cats: ${e.message}", e)
        }
    }

}