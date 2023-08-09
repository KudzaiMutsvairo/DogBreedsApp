package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository.DogBreedsRepository
import javax.inject.Inject

class FetchDogBreedsLocalUseCase @Inject constructor(
    private val repository: DogBreedsRepository,
) {
    suspend operator fun invoke() = repository.getAllDogBreedsFromCache()
}
