package com.kmutswairo.dogbreedsapp.data.mapper

import com.kmutswairo.dogbreedsapp.data.local.favourites.FavouriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.domain.model.FavouriteDogBreed

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
