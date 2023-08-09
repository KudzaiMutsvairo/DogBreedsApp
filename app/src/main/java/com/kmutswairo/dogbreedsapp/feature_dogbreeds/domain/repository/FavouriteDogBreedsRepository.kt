package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavouriteDogBreedsRepository {
    suspend fun insertFavouriteDogBreed(favDogBreed: FavouriteDogBreed): Resource<Long>
    suspend fun deleteFavouriteDogBreed(id: Int): Resource<Boolean>
    suspend fun getAllFavouriteDogBreeds(): Flow<Resource<List<FavouriteDogBreed>>>
}
