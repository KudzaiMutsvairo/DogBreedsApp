package com.kmutswairo.dogbreedsapp.presentation.viewbreed.events

sealed class ViewDogBreedEvent {
    class Load(val breedName: String) : ViewDogBreedEvent()
}
