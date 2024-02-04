package com.example.catapp.repository

import android.util.Log
import com.example.catapp.data.remote.CatBreedsApi
import com.example.catapp.constants.Constant
import com.example.catapp.model.BreedsListDto
import com.example.catapp.data.local.CatDAO
import com.example.catapp.data.local.CatDataEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CatsRepository @Inject constructor(private val catApiService: CatBreedsApi,private val catDao: CatDAO) {

    private val breedsData = MutableStateFlow<List<BreedsListDto>>(emptyList())
    val breedData: StateFlow<List<BreedsListDto>>
        get() = breedsData

    suspend fun getCatsData(pageNumber:Int) : List<BreedsListDto>{
        val catlist = catApiService.getCatsData(
            10,
            1,
            pageNumber,
            Constant.apiKey
        )

        mapResponseToEntities(catlist)

        return catApiService.getCatsData(
            10,
            1,
            pageNumber,
            Constant.apiKey
        )
    }

    suspend fun getSelectedBreedDetails(breedId: String,pageNumber: Int) : List<BreedsListDto>{
        val breedList = catApiService.getBreeds(
            50,
            breedId,
            pageNumber,
            Constant.apiKey
        )

        Log.d("breed list",breedList.toString())
        if (breedList.isEmpty() != null) {
            breedsData.emit(breedList)
            return breedList
        }
        else{
            return emptyList()
        }
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
}
