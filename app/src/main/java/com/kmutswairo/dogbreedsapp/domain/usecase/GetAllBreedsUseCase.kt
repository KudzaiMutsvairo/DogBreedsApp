package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllBreedsUseCase @Inject constructor(
    private val fetchDogBreedsLocalUseCase: FetchDogBreedsLocalUseCase,
    private val fetchRemoteDogBreedsUseCase: FetchRemoteDogBreedsUseCase,
    private val saveDogBreedsToCacheUseCase: SaveDogBreedsToCacheUseCase,
) {
    suspend operator fun invoke(): Flow<List<DogBreed>> {
        return flow {
            fetchDogBreedsLocalUseCase().collect { localDogBreeds ->
                if (localDogBreeds.isEmpty()) {
                    fetchRemoteDogBreedsUseCase().collect { remoteDogBreeds ->
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
