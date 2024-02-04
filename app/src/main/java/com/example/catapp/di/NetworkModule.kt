package com.example.catapp.di

import android.content.Context
import androidx.room.Room
import com.example.catapp.data.remote.CatBreedsApi
import com.example.catapp.constants.Constant
import com.example.catapp.data.local.CatDAO
import com.example.catapp.data.local.CatDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesCatBreedApi(retrofit: Retrofit): CatBreedsApi {
        return retrofit.create(CatBreedsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCatDao(database: CatDatabase): CatDAO {
        return database.catDao
    }

    @Singleton
    @Provides
    fun provideCatDatabase(@ApplicationContext context: Context): CatDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CatDatabase::class.java,
            "catDatabase" // Specify your desired database name
        )
            .build()
    }
}