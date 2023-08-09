package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist.DogBreedsListScreen
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed.FavouriteDogBreedsScreen
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed.ViewDogBreedScreen

@Composable
fun Navigation(
    navController: NavHostController,
    showSnackbar: (message: String, duration: SnackbarDuration) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.DogBreedsList.route,
    ) {
        composable(
            route = Screen.DogBreedsList.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) {
            DogBreedsListScreen(
                showSnackbar = showSnackbar,
                navController = navController,
            )
        }

        composable(
            route = Screen.FavouriteDogBreeds.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) {
            FavouriteDogBreedsScreen(
                showSnackbar = showSnackbar,
                navController = navController,
            )
        }

        composable(
            route = Screen.ViewDogBreed.route + "/{name}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700),
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(700),
                )
            },
        ) { entry ->
            ViewDogBreedScreen(
                showSnackbar = showSnackbar,
                name = entry.arguments?.getString("name")!!,
            )
        }
    }
}
