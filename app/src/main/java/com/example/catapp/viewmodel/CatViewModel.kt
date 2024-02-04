package com.example.catapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.catapp.common.AppState
import com.example.catapp.data.local.CatDAO
import com.example.catapp.data.local.CatDataEntity
import com.example.catapp.model.BreedsListDomain
import com.example.catapp.model.BreedsListDto
import com.example.catapp.repository.CatsRepository
import com.example.catapp.usecase.GetAllCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val catDao: CatDAO, application: Application
) : ViewModel() {
    private val _cats = MutableStateFlow<List<BreedsListDto>>(emptyList())
    val cats: StateFlow<List<BreedsListDto>> get() = _cats.asStateFlow()

    private val context = application.applicationContext
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private var currentPage = 1

    init {
        loadCats()
    }

    fun loadCats() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (isInternetAvailable(context)) {
                    val newCats = repository.getCatsData(currentPage)
                    _cats.value = _cats.value + newCats
                    currentPage++
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    suspend fun isInternetAvailable(context: Context): Boolean {
        return withContext(Dispatchers.IO) {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        }
    }

}
