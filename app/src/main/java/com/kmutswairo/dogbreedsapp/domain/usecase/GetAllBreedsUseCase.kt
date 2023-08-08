package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBreedsUseCase @Inject constructor(
    private val fetchDogBreedsLocalUseCase: FetchDogBreedsLocalUseCase,
    private val fetchRemoteDogBreedsUseCase: FetchRemoteDogBreedsUseCase,
    private val saveDogBreedsToCacheUseCase: SaveDogBreedsToCacheUseCase,
) {
    suspend operator fun invoke(): Flow<Resource<List<DogBreed>>> {
        // First try and fetch from local cache
        return flow {
            fetchDogBreedsLocalUseCase().collect { localDogBreeds ->
                // If local cache is empty, fetch from remote
                if (localDogBreeds.data.isNullOrEmpty()) {
                    fetchRemoteDogBreedsUseCase().collect { remoteDogBreeds ->
                        // If remote fetch is successful, save to local cache
                        remoteDogBreeds.data?.let {
                            saveDogBreedsToCacheUseCase(remoteDogBreeds.data)
                        }
                    }
                } else {
                    emit(localDogBreeds)
                }
            }
        }
    }
}
