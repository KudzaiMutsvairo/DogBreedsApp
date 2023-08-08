package com.kmutswairo.dogbreedsapp.domain.model

data class FavouriteDogBreed(
    val id: Int,
    val name: String,
    val subBreeds: List<String>,
)
