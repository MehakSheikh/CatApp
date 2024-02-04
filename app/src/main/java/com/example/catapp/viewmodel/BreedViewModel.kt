package com.example.catapp.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.model.BreedsListDto
import com.example.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _breedsData = MutableStateFlow<List<BreedsListDto>>(emptyList())
    val breedsData: StateFlow<List<BreedsListDto>> get() = _breedsData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    init {

        loadBreedSpecificCats()
    }

    private var currentPage = 1
    fun loadBreedSpecificCats() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val breedId = savedStateHandle.get<String>("breedId") ?: "beng"
                val newCats = repository.getSelectedBreedDetails(breedId,currentPage)

                _breedsData.value = breedsData.value + newCats
                Log.d("checking breed detail mehak",breedsData.value.toString())
                currentPage++

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
