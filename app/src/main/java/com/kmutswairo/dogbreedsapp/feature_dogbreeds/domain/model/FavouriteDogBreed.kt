package com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model

data class FavouriteDogBreed(
    val id: Int,
    val name: String,
    val subBreeds: List<String>,
)
