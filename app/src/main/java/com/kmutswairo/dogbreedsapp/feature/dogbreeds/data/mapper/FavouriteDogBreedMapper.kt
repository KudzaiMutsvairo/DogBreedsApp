package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.mapper

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.favourites.FavouriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.FavouriteDogBreed

fun FavouriteDogBreed.toFavoriteDogBreedEntity(): FavouriteDogBreedEntity {
    return FavouriteDogBreedEntity(
        id = id ?: 0,
        breedName = name,
        subBreeds = subBreeds,
    )
}

fun FavouriteDogBreedEntity.toFavouriteDogBreed(): FavouriteDogBreed {
    return FavouriteDogBreed(
        id = id ?: 0,
        name = breedName,
        subBreeds = subBreeds,
    )
}

fun DogBreed.toFavouriteDogBreed(): FavouriteDogBreed {
    return FavouriteDogBreed(
        id = id ?: 0,
        name = name,
        subBreeds = subBreeds,
    )
}
