package com.example.catapp.usecase

import com.example.catapp.common.AppState
import com.example.catapp.model.BreedsListDomain
import com.example.catapp.repository.CatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetChoosenBreedUseCase(
    private val catsRepository: CatsRepository
) {
  //  val breedId = savedStateHandle.get<String>("breedId") ?: "beng"
    suspend fun invoke(): Flow<AppState<List<BreedsListDomain>>> = flow {
        emit(AppState.loading())
        try {
            emit(
                AppState.idle(
                  //  catsRepository.getSelectedBreedDetails("beng").map { it.toDomain() }
                )
            )
        } catch (e: Exception) {
            emit(AppState.error(e))
        }
    }
}