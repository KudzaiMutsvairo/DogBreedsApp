package com.kmutswairo.dogbreedsapp.presentation.breedslist

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed

data class DogBreedsState(
    val breeds: List<DogBreed> = emptyList(),
    val isLoading: Boolean = false,
    val message: String? = null,
)
