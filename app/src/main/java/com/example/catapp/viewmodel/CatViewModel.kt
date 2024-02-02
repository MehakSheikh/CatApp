package com.example.catapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.common.AppState
import com.example.catapp.domain.BreedsListDomain
import com.example.catapp.usecase.GetAllCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(private val repository: GetAllCatsUseCase) : ViewModel() {

//    val _catData: Flow<State<List<BreedsListDomain>>>
    private val catData = MutableStateFlow<AppState<List<BreedsListDomain>>>(value = AppState.loading())

    init {
        viewModelScope.launch {
            repository.invoke().collect{catData.value=it }
        }
    }
    fun catListState(): StateFlow<AppState<List<BreedsListDomain>>> = catData

}
