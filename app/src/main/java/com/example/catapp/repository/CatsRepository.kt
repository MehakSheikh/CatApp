package com.example.catapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.CatBreedsApi
import com.example.catapp.model.BreedsListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CatsRepository @Inject constructor(private val catApiService: CatBreedsApi) {

    private val _catData = MutableStateFlow<List<BreedsListItem>>(emptyList())
    val catData: StateFlow<List<BreedsListItem>>
        get() = _catData

    private val _breedData = MutableStateFlow<List<BreedsListItem>>(emptyList())
    val breedData: StateFlow<List<BreedsListItem>>
        get() = _breedData

    suspend fun getCatsData() {
        val catList = catApiService.getCatsData(
            20,
            1,
            "live_6mPO3thEhQQp5lLKHJzvSD4tyrPCiRg28D3VkxiURvMclrSV5AzIckEuIvCzSNce"
        )
        if (catList.isEmpty() != null)
            _catData.emit(catList)
    }

    suspend fun getSelectedBreedDetails(breedId: String) {
        val breedList = catApiService.getBreeds(
            11,
            breedId,
            "live_6mPO3thEhQQp5lLKHJzvSD4tyrPCiRg28D3VkxiURvMclrSV5AzIckEuIvCzSNce"
        )
        if (breedList.isEmpty() != null)
            _breedData.emit(breedList)
    }
}