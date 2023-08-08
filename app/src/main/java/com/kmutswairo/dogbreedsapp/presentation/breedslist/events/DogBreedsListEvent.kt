package com.kmutswairo.dogbreedsapp.presentation.breedslist.events

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed

sealed class DogBreedsListEvent {
    object OnRefresh : DogBreedsListEvent()
    class OnClickFavourite(val breed: DogBreed) : DogBreedsListEvent()
}
