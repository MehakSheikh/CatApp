package com.example.catapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.api.CatBreedsApi
import com.example.catapp.model.BreedsListItem
import com.example.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val repository: CatsRepository) : ViewModel() {

    val _catData: StateFlow<List<BreedsListItem>>
        get() = repository.catData

    init {
        viewModelScope.launch {
            repository.getCatsData()
        }
    }


}
