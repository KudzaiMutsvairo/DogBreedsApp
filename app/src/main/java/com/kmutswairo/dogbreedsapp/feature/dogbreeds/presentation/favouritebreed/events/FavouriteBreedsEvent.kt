package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.favouritebreed.events

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.FavouriteDogBreed

sealed class FavouriteBreedsEvent {
    data class RemoveFavouriteBreed(val breed: FavouriteDogBreed) : FavouriteBreedsEvent()
}
