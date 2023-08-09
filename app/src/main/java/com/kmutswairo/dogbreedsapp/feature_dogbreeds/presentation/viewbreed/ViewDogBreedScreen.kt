package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.LoadingComponent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.NoDataComponent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed.events.ViewDogBreedEvent
import kotlin.random.Random.Default.nextInt

@Composable
fun ViewDogBreedScreen(
    name: String,
    viewModel: ViewDogBreedViewModel = hiltViewModel(),
    scaffoldPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(name) {
        viewModel.onEvent(ViewDogBreedEvent.Load(breedName = name))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
    ) {
        if (uiState.isLoading) {
            LoadingComponent()
        }

        if (uiState.dogBreeds.isEmpty()) {
            NoDataComponent()
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    model = uiState.dogBreeds[nextInt(0, uiState.dogBreeds.size)],
                    contentDescription = "Image of $name",
                )
            }
        }
    }
}
