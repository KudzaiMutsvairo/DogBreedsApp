package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository.ViewBreedRepository
import javax.inject.Inject

class GetBreedImagesUseCase @Inject constructor(
    private val repository: ViewBreedRepository,
) {
    suspend operator fun invoke(breedName: String) = repository.getBreedImages(breedName)
}
