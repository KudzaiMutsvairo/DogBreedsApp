package com.kmutswairo.dogbreedsapp.domain.model

data class DogBreed(
    val id: Int? = null,
    val name: String,
    val subBreeds: List<String>,
    val isFavourite: Boolean = false,
)
