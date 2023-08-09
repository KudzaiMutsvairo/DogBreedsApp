package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed.events

import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.FavouriteDogBreed

sealed class FavouriteBreedsEvent {
    data class RemoveFavouriteBreed(val breed: FavouriteDogBreed) : FavouriteBreedsEvent()
}
