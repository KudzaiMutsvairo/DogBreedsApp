package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.viewbreedimages

data class ViewDogBreedState(
    val isLoading: Boolean = false,
    val dogBreeds: List<String> = emptyList(),
    val message: String? = null,
)
