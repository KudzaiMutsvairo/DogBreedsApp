package com.kmutswairo.dogbreedsapp.domain.repository

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface DogBreedsRepository {
    suspend fun getAllDogBreedsFromCache(): Flow<Resource<List<DogBreed>>>
    suspend fun getBreedImages(breed: String): Flow<Resource<List<String>>>
    suspend fun refreshAllDogBreeds(): Flow<Resource<List<DogBreed>>>
    suspend fun deleteAllDogBreeds(): Resource<Int>
    suspend fun fetchRemoteDogBreeds(): Flow<Resource<List<DogBreed>>>
    suspend fun saveDogBreedsToCache(dogBreeds: List<DogBreed>): Boolean
}
