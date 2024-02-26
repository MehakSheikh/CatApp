package com.example.catapp.usecase
import com.example.catapp.common.AppState
import com.example.catapp.model.BreedsListDomain
import com.example.catapp.mappers.toDomain
import com.example.catapp.repository.CatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllCatsUseCase(private val catsRepository: CatsRepository) {

    suspend fun invoke(): Flow<AppState<List<BreedsListDomain>>> = flow {
        emit(AppState.loading())
        try {
            emit(
                AppState.idle(
                    catsRepository.getCatsData(1).map { it.toDomain() } //mapping data from API to our Domain function for consuming it on our UI
                )
            )
        } catch (e: Exception) {
            emit(AppState.error(e))
        }
    }
}