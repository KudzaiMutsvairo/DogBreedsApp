package com.kmutswairo.dogbreedsapp.presentation.favouritebreed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kmutswairo.dogbreedsapp.presentation.components.FavouriteDogBreedItem
import com.kmutswairo.dogbreedsapp.presentation.components.LoadingComponent
import com.kmutswairo.dogbreedsapp.presentation.components.NoDataComponent
import com.kmutswairo.dogbreedsapp.presentation.components.ScreenTitle
import com.kmutswairo.dogbreedsapp.presentation.favouritebreed.events.FavouriteBreedsEvent
import com.kmutswairo.dogbreedsapp.presentation.navigation.Screen

@Composable
fun FavouriteDogBreedsScreen(
    viewModel: FavouriteDogBreedsViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration) -> Unit,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        ScreenTitle(title = "Favourites")
        Spacer(modifier = Modifier.height(20.dp))

        if (uiState.isLoading) {
            LoadingComponent()
        }

        if (uiState.breeds.isEmpty()) {
            NoDataComponent()
        }

        if (uiState.breeds.isNotEmpty()) {
            LazyColumn {
                items(uiState.breeds) { breed ->
                    FavouriteDogBreedItem(
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.ViewDogBreed.route + "/${breed.name}")
                        },
                        breed = breed,
                        onFavoriteToggle = {
                            viewModel.onEvent(FavouriteBreedsEvent.RemoveFavouriteBreed(breed))
                        },
                    )
                }
            }
        }

        if (!uiState.message.isNullOrEmpty()) {
            uiState.message?.let { message ->
                showSnackbar(message, SnackbarDuration.Short)
            }
        }
    }
}
