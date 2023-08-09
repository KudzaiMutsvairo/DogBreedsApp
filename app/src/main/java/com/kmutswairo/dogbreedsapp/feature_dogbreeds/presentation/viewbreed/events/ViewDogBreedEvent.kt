package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed.events

sealed class ViewDogBreedEvent {
    class Load(val breedName: String) : ViewDogBreedEvent()
}
