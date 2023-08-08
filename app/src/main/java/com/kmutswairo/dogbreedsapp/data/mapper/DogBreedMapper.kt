package com.kmutswairo.dogbreedsapp.data.mapper

import com.kmutswairo.dogbreedsapp.data.local.cache.DogBreedsEntity
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed

fun DogBreedsEntity.toDogBreed(): DogBreed {
    return DogBreed(
        id = id!!,
        name = breedName,
        subBreeds = subBreeds,
    )
}

fun DogBreed.toDogBreedsEntity(): DogBreedsEntity {
    return DogBreedsEntity(
        id = id,
        breedName = name,
        subBreeds = subBreeds,
    )
}
