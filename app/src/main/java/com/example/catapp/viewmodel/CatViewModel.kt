package com.example.catapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.local.CatDAO
import com.example.catapp.data.local.CatDataEntity
import com.example.catapp.model.BreedsListDto
import com.example.catapp.repository.CatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatsRepository,
    private val catDao: CatDAO,
    application: Application
) : ViewModel() {

    private val _cats = MutableStateFlow<List<BreedsListDto>>(emptyList())
    val cats: StateFlow<List<BreedsListDto>>
        get() = _cats.asStateFlow()

    private val _catDataFromRoom = MutableStateFlow<List<CatDataEntity>>(emptyList())
    val catDataFromRoom: StateFlow<List<CatDataEntity>>
        get() = _catDataFromRoom.asStateFlow()

    private val context = application.applicationContext

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private var currentPage = 1
    private var roomPage = 1

    init {
        loadCats()
    }

    fun loadCats() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val totalSize = catDao.getTotalSize()

                if (isInternetAvailable(context)) {
                    val newCats = repository.getCatsData(currentPage)
                    _cats.value = _cats.value + newCats
                    currentPage++
                } else {    //checking if all images have been shown or not.
                    val pageSize = 10
                    val offset = roomPage * pageSize
                    var totalCountFromRoom = 10
                    val localCats = catDao.getPaginatedCats(pageSize, offset)
                    Log.d("data in without internet Mehak", localCats.toString())
                    _catDataFromRoom.value = _catDataFromRoom.value + localCats
                    if (totalSize >= totalCountFromRoom) {

                        return@launch
                    } else {
                        roomPage++
                    }

                    totalCountFromRoom = totalCountFromRoom + 10
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
