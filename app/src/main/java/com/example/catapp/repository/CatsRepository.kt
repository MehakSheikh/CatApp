package com.example.catapp.repository

import com.example.catapp.api.CatBreedsApi
import com.example.catapp.domain.BreedsListDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CatsRepository @Inject constructor(private val catApiService: CatBreedsApi) {

//    private val _catData = MutableStateFlow<List<BreedsListDto>>(emptyList())
//    val catData: StateFlow<List<BreedsListDto>>
//        get() = _catData

    private val _breedData = MutableStateFlow<List<BreedsListDto>>(emptyList())
    val breedData: StateFlow<List<BreedsListDto>>
        get() = _breedData

    suspend fun getCatsData() : List<BreedsListDto>{
      return catApiService.getCatsData(
            50,
            1,
            "live_6mPO3thEhQQp5lLKHJzvSD4tyrPCiRg28D3VkxiURvMclrSV5AzIckEuIvCzSNce"
        )

    }

    suspend fun getSelectedBreedDetails(breedId: String) {
        val breedList = catApiService.getBreeds(
            50,
            breedId,
            "live_6mPO3thEhQQp5lLKHJzvSD4tyrPCiRg28D3VkxiURvMclrSV5AzIckEuIvCzSNce"
        )
        if (breedList.isEmpty() != null)
            _breedData.emit(breedList)
    }
}