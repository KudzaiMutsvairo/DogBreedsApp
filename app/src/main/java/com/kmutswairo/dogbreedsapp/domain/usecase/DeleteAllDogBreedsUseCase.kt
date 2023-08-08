package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import javax.inject.Inject

class DeleteAllDogBreedsUseCase @Inject constructor(
    private val repository: DogBreedsRepository,
) {
    suspend operator fun invoke(): Boolean = repository.deleteAllDogBreeds()
}
