package com.example.catapp.di

import com.example.catapp.repository.CatsRepository
import com.example.catapp.usecase.GetAllCatsUseCase
import com.example.catapp.usecase.GetChoosenBreedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideCatUseCase(catsRepository: CatsRepository): GetAllCatsUseCase {
        return GetAllCatsUseCase(
            catsRepository
        )
    }
 /*   @Singleton
    @Provides
    fun provideChosenBreedUseCase(catsRepository: CatsRepository): GetChoosenBreedUseCase {
        return GetChoosenBreedUseCase(
            catsRepository
        )
    }*/
}

