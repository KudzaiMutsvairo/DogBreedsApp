package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.breedslist

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed

data class DogBreedsState(
    val breeds: List<DogBreed> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
)
