package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.viewbreed

data class ViewDogBreedState(
    val isLoading: Boolean = false,
    val dogBreeds: List<String> = emptyList(),
    val message: String? = null,
)
