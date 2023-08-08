package com.kmutswairo.dogbreedsapp.presentation.breedslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kmutswairo.dogbreedsapp.presentation.breedslist.events.DogBreedsListEvent
import com.kmutswairo.dogbreedsapp.presentation.components.DogBreedItem
import com.kmutswairo.dogbreedsapp.presentation.components.LoadingComponent
import com.kmutswairo.dogbreedsapp.presentation.components.NoDataComponent
import com.kmutswairo.dogbreedsapp.presentation.components.ScreenTitle
import com.kmutswairo.dogbreedsapp.presentation.navigation.Screen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogBreedsListScreen(
    viewModel: DogBreedsViewModel = hiltViewModel(),
    showSnackbar: (String, SnackbarDuration) -> Unit,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isLoading,
        onRefresh = { viewModel.onEvent(DogBreedsListEvent.OnRefresh) },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        ScreenTitle(title = "Dog Breeds")
        Spacer(modifier = Modifier.height(20.dp))

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
                LazyColumn {
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

        if (!uiState.message.isNullOrEmpty()) {
            uiState.message?.let { message ->
                showSnackbar(message, SnackbarDuration.Short)
            }
        }
    }
}
