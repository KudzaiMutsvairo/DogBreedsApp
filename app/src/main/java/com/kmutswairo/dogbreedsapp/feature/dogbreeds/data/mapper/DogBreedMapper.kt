package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.mapper

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.cache.DogBreedsEntity
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed

fun DogBreedsEntity.toDogBreed(): DogBreed {
    return DogBreed(
        id = id!!,
        name = name,
        subBreeds = subBreeds,
    )
}

fun DogBreed.toDogBreedsEntity(): DogBreedsEntity {
    return DogBreedsEntity(
        id = id,
        name = name,
        subBreeds = subBreeds,
    )
}
