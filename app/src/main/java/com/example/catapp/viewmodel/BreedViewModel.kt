package com.example.catapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.domain.BreedsListDto
import com.example.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val _breedsData: StateFlow<List<BreedsListDto>>
    get() = repository.breedData

    init {
        viewModelScope.launch {
            val breedId = savedStateHandle.get<String>("breedId") ?: "beng"
            repository.getSelectedBreedDetails(breedId)
        }
    }
}
