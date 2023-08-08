package com.kmutswairo.dogbreedsapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null,
) {
    object DogBreedsList : Screen(
        route = "dog_breeds_home",
        title = "Breeds",
        icon = Icons.Outlined.Home,
    )

    object FavouriteDogBreeds : Screen(
        route = "favourite_dog_breeds",
        title = "Favourites",
        icon = Icons.Outlined.Star,
    )

    object ViewDogBreed : Screen(
        route = "view_dog_breed",
        title = "View Dog Breed",
    )
}
