package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.breedslist.events

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed

sealed class DogBreedsListEvent {
    object OnRefresh : DogBreedsListEvent()
    class OnClickFavourite(val breed: DogBreed) : DogBreedsListEvent()
}
