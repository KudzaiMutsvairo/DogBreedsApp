package com.kmutswairo.dogbreedsapp.data.mapper

import com.kmutswairo.dogbreedsapp.data.local.cache.DogBreedsEntity
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed

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
