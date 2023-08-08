package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import javax.inject.Inject

class SaveDogBreedsToCacheUseCase @Inject constructor(
    private val repository: DogBreedsRepository,
) {
    suspend operator fun invoke(breeds: List<DogBreed>) = repository.saveDogBreedsToCache(
        breeds,
    )
}
