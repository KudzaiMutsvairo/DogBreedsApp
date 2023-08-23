package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.viewbreedimages.events

sealed class ViewDogBreedEvent {
    class Load(val breedName: String) : ViewDogBreedEvent()
}
