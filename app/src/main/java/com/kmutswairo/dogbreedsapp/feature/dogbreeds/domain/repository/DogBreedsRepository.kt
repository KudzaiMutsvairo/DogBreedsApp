package com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Resource
import kotlinx.coroutines.flow.Flow

interface DogBreedsRepository {
    suspend fun getAllDogBreedsFromCache(): Flow<List<DogBreed>>
    suspend fun deleteAllDogBreeds(): Boolean
    suspend fun fetchRemoteDogBreeds(): Flow<Resource<List<DogBreed>>>
    suspend fun saveDogBreedsToCache(dogBreeds: List<DogBreed>): Boolean
    suspend fun saveSingleDogBreedToCache(dogBreed: DogBreed): Boolean
}
