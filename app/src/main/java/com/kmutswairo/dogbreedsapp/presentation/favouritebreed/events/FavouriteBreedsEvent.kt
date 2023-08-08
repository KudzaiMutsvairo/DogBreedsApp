package com.kmutswairo.dogbreedsapp.presentation.favouritebreed.events

import com.kmutswairo.dogbreedsapp.domain.model.FavouriteDogBreed

sealed class FavouriteBreedsEvent {
    data class RemoveFavouriteBreed(val breed: FavouriteDogBreed) : FavouriteBreedsEvent()
}
