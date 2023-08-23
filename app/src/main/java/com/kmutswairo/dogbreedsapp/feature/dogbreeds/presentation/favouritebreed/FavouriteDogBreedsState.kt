package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.favouritebreed

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.FavouriteDogBreed

data class FavouriteDogBreedsState(
    val breeds: List<FavouriteDogBreed> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
)
