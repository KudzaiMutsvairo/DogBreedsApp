package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import javax.inject.Inject

class FetchRemoteDogBreedsUseCase @Inject constructor(
    private val repository: DogBreedsRepository
) {
    suspend operator fun invoke() = repository.fetchRemoteDogBreeds()
}