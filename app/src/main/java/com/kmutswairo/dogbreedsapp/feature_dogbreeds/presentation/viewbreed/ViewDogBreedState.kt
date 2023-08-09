package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed

data class ViewDogBreedState(
    val isLoading: Boolean = false,
    val dogBreeds: List<String> = emptyList(),
    val message: String? = null,
)
