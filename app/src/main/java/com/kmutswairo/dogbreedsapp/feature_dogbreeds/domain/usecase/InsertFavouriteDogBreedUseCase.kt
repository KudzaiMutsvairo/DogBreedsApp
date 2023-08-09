package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.repository.FavouriteDogBreedsRepository
import javax.inject.Inject

class InsertFavouriteDogBreedUseCase @Inject constructor(
    private val repository: FavouriteDogBreedsRepository,
) {
    suspend operator fun invoke(dogBreed: FavouriteDogBreed) =
        repository.insertFavouriteDogBreed(dogBreed)
}
