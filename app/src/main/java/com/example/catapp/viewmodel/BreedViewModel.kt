package com.example.catapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.model.BreedsListItem
import com.example.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(private val repository: CatsRepository) : ViewModel() {

    val _breedsData: StateFlow<List<BreedsListItem>>
    get() = repository.breedData

    init {
        viewModelScope.launch {
            repository.getSelectedBreedDetails("beng")
        }
    }
}
