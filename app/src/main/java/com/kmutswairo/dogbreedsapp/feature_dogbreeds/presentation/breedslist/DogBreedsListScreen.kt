package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist.events.DogBreedsListEvent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.DogBreedItem
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.LoadingComponent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.NoDataComponent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components.ScreenTitle
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.navigation.Screen
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogBreedsListScreen(
    viewModel: DogBreedsViewModel = hiltViewModel(),
    navController: NavController,
    scaffoldPadding: PaddingValues,
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { viewModel.onEvent(DogBreedsListEvent.OnRefresh) },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
    ) {
        ScreenTitle(title = "Dog Breeds")

        if (uiState.isLoading) {
            LoadingComponent()
        }

        if (uiState.breeds.isEmpty()) {
            NoDataComponent()
        }

        if (uiState.breeds.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState),
            ) {
                LazyColumn(
                    modifier = Modifier,
                ) {
                    items(uiState.breeds) { breed ->
                        DogBreedItem(
                            modifier = Modifier.clickable {
                                navController.navigate(
                                    Screen.ViewDogBreed.route + "/${breed.name}",
                                )
                            },
                            breed = breed,
                            onFavoriteToggle = {
                                viewModel.onEvent(DogBreedsListEvent.OnClickFavourite(breed))
                            },
                        )
                    }
                }

                PullRefreshIndicator(
                    refreshing = uiState.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = if (uiState.isLoading) Color.Red else Color.Green,
                )
            }
        }

        if (uiState.message != null) {
            LaunchedEffect(uiState.message) {
                delay(2000)
                viewModel.clearMessage()
            }

            AlertDialog(
                onDismissRequest = {
                    viewModel.clearMessage()
                },
                title = { Text(text = "DogBreeds") },
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
