package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.DogBreed

data class DogBreedsState(
    val breeds: List<DogBreed> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
)
