package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.repository.FavouriteDogBreedsRepository
import javax.inject.Inject

class GetAllFavouriteDogBreedUseCase @Inject constructor(
    private val repository: FavouriteDogBreedsRepository,
) {
    suspend operator fun invoke() = repository.getAllFavouriteDogBreeds()
}
