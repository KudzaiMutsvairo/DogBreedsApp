package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository.DogBreedsRepository
import javax.inject.Inject

class SaveDogBreedsToCacheUseCase @Inject constructor(
    private val repository: DogBreedsRepository,
) {
    suspend operator fun invoke(breeds: List<DogBreed>) = repository.saveDogBreedsToCache(
        breeds,
    )
}
