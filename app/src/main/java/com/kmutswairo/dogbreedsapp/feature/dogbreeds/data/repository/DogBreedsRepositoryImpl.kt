package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.repository

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.cache.DogBreedsDao
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.mapper.toDogBreedsEntity
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.DogApi
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.DogBreedsRepository
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogBreedsRepositoryImpl @Inject constructor(
    private val dogApi: DogApi,
    private val dogBreedsDao: DogBreedsDao,
) : DogBreedsRepository {

    override suspend fun getAllDogBreedsFromCache(): Flow<List<DogBreed>> {
        return dogBreedsDao.getAllDogBreeds()
    }

    override suspend fun deleteAllDogBreeds(): Boolean {
        return dogBreedsDao.deleteAllDogBreeds() > 0
    }

    override suspend fun fetchRemoteDogBreeds(): Flow<Resource<List<DogBreed>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = dogApi.getAllDogBreeds()
                if (response.isSuccessful) {
                    val responseData = response.body()?.message
                    val data = responseData?.entries?.map { (key, value) ->
                        DogBreed(
                            name = key,
                            subBreeds = value,
                        )
                    }
                    emit(Resource.Success(data))
                } else {
                    emit(Resource.Error("An error occurred while fetching remote data"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred while fetching remote data"))
            }
        }
    }

    override suspend fun saveDogBreedsToCache(dogBreeds: List<DogBreed>): Boolean {
        return dogBreedsDao.insertDogBreeds(dogBreeds.map { it.toDogBreedsEntity() }).isNotEmpty()
    }

    override suspend fun saveSingleDogBreedToCache(dogBreed: DogBreed): Boolean {
        return dogBreedsDao.insertSingleDogBreed(dogBreed.toDogBreedsEntity()) > 0
    }
}
