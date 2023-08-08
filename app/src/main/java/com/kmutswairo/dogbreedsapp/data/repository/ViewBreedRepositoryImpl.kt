package com.kmutswairo.dogbreedsapp.data.repository

import com.kmutswairo.dogbreedsapp.data.remote.DogApi
import com.kmutswairo.dogbreedsapp.domain.repository.ViewBreedRepository
import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewBreedRepositoryImpl @Inject constructor(
    private val dogApi: DogApi,
) : ViewBreedRepository {
    override suspend fun getBreedImages(breed: String): Flow<Resource<List<String>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val response = dogApi.getDogBreedImages(breed)
                if (response.isSuccessful) {
                    emit(Resource.Success(data = response.body()?.message ?: listOf()))
                } else {
                    emit(Resource.Error("An error occurred while fetching data"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred while fetching data"))
            }
        }
    }
}
