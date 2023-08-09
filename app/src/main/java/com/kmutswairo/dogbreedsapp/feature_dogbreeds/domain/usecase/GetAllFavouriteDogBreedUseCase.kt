package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository.FavouriteDogBreedsRepository
import javax.inject.Inject

class GetAllFavouriteDogBreedUseCase @Inject constructor(
    private val repository: FavouriteDogBreedsRepository,
) {
    suspend operator fun invoke() = repository.getAllFavouriteDogBreeds()
}
