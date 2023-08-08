package com.kmutswairo.dogbreedsapp.data.repository

import com.kmutswairo.dogbreedsapp.data.local.DogBreedsDatabase
import com.kmutswairo.dogbreedsapp.data.local.cache.DogBreedsDao
import com.kmutswairo.dogbreedsapp.data.mapper.toDogBreed
import com.kmutswairo.dogbreedsapp.data.mapper.toDogBreedsEntity
import com.kmutswairo.dogbreedsapp.data.remote.DogApi
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DogBreedsRepositoryImpl @Inject constructor(
    private val dogApi: DogApi,
    private val dogBreedsDao: DogBreedsDao,
) : DogBreedsRepository {

    override suspend fun getAllDogBreedsFromCache(): Flow<Resource<List<DogBreed>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val dogBreeds = dogBreedsDao.getAllDogBreeds()
                emit(
                    Resource.Success(
                        data = dogBreeds,
                    ),
                )
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred while fetching data"))
            }
        }
    }

    override suspend fun deleteAllDogBreeds(): Resource<Int> {
        return try {
            val result = dogBreedsDao.deleteAllDogBreeds()
            Resource.Success(data = result)
        } catch (e: Exception) {
            Resource.Error("An error occurred while deleting all dog breeds")
        }
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
}