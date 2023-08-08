package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshDogBreedsUseCase @Inject constructor(
    private val fetchRemoteDogBreedsUseCase: FetchRemoteDogBreedsUseCase,
    private val deleteAllDogBreedsUseCase: DeleteAllDogBreedsUseCase,
    private val saveDogBreedsToCacheUseCase: SaveDogBreedsToCacheUseCase,
) {
    suspend operator fun invoke(): Flow<Resource<Boolean>> {
        return flow {
            if (deleteAllDogBreedsUseCase()) {
                fetchRemoteDogBreedsUseCase().collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            emit(Resource.Loading())
                        }

                        is Resource.Success -> {
                            result.data?.let {
                                if (saveDogBreedsToCacheUseCase(it)) {
                                    emit(Resource.Success(data = true))
                                } else {
                                    emit(Resource.Error("Error saving data to cache"))
                                }
                            } ?: emit(Resource.Error("Error fetching remote data"))
                        }

                        is Resource.Error -> {
                            emit(Resource.Error("Error fetching remote data"))
                        }
                    }
                }
            } else {
                emit(Resource.Error("Error clearing cache"))
            }
        }
    }
}
