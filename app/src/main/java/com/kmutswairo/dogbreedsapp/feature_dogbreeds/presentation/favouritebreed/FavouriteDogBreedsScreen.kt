package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.FavouriteDogBreedItem
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.LoadingComponent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.NoDataComponent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.ScreenTitle
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.favouritebreed.events.FavouriteBreedsEvent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun FavouriteDogBreedsScreen(
    viewModel: FavouriteDogBreedsViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
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

        if (uiState.message != null) {
            LaunchedEffect(uiState.message) {
                delay(2500)
                viewModel.clearMessage()
            }

            AlertDialog(
                onDismissRequest = {
                    viewModel.clearMessage()
                },
                title = { Text(text = "Dialog Title") },
                text = { Text(text = uiState.message!!) },
                confirmButton = {
                    Button(
                        onClick = { viewModel.clearMessage() },
                    ) {
                        Text(text = "OK")
                    }
                },
                modifier = Modifier.fillMaxSize().wrapContentSize(),
            )
        }
    }
}
