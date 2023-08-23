package com.kmutswairo.dogbreedsapp.di

import android.app.Application
import androidx.room.Room
import com.kmutswairo.dogbreedsapp.BuildConfig
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.DogBreedsDatabase
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.cache.DogBreedsDao
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.favourites.FavDogBreedsDao
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.DogApi
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.repository.DogBreedsRepositoryImpl
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.repository.FavouriteDogBreedsRepositoryImpl
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.repository.ViewBreedRepositoryImpl
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.DogBreedsRepository
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.FavouriteDogBreedsRepository
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.ViewBreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDogApi(): DogApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DogApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDogBreedsDatabase(app: Application): DogBreedsDatabase {
        return Room.databaseBuilder(
            app,
            DogBreedsDatabase::class.java,
            "dogbreedsdb.db",
        ).build()
    }

    @Singleton
    @Provides
    fun provideDogBreedsDao(db: DogBreedsDatabase) = db.dogBreedsDao()

    @Singleton
    @Provides
    fun provideFavouriteDogBreedsDao(db: DogBreedsDatabase) = db.favDogBreedsDao()

    @Singleton
    @Provides
    fun provideDogBreedsRepository(
        api: DogApi,
        dogBreedsDao: DogBreedsDao,
    ): DogBreedsRepository {
        return DogBreedsRepositoryImpl(api, dogBreedsDao)
    }

    @Singleton
    @Provides
    fun provideFavoriteDogBreedsRepository(
        favouriteDogBreedsDao: FavDogBreedsDao,
    ): FavouriteDogBreedsRepository {
        return FavouriteDogBreedsRepositoryImpl(favouriteDogBreedsDao)
    }

    @Singleton
    @Provides
    fun provideViewBreedRepository(
        api: DogApi,
    ): ViewBreedRepository {
        return ViewBreedRepositoryImpl(api)
    }
}
