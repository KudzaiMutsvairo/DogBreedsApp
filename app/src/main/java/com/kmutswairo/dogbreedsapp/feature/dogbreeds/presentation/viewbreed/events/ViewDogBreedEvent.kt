package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.viewbreed.events

sealed class ViewDogBreedEvent {
    class Load(val breedName: String) : ViewDogBreedEvent()
}
