package com.kmutswairo.dogbreedsapp.presentation.favouritebreed

import com.kmutswairo.dogbreedsapp.domain.model.FavouriteDogBreed

data class FavouriteDogBreedsState(
    val breeds: List<FavouriteDogBreed> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
)
