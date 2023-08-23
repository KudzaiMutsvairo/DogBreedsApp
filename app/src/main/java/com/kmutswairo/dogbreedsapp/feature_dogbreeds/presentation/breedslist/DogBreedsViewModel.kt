package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.mapper.toFavouriteDogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.DeleteFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.GetAllBreedsUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.InsertFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.RefreshDogBreedsUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.breedslist.events.DogBreedsListEvent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedsViewModel @Inject constructor(
    private val getAllBreedsUseCase: GetAllBreedsUseCase,
    private val refreshDogBreedsUseCase: RefreshDogBreedsUseCase,
    private val insertFavouriteDogBreedUseCase: InsertFavouriteDogBreedUseCase,
    private val deleteFavouriteDogBreedUseCase: DeleteFavouriteDogBreedUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(DogBreedsState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = DogBreedsState(
            isLoading = true,
            breeds = emptyList(),
            message = null,
        ),
    )

    init {
        getDogBreeds()
    }

    fun onEvent(event: DogBreedsListEvent) {
        when (event) {
            is DogBreedsListEvent.OnRefresh -> {
                refreshDogBreeds()
            }

            is DogBreedsListEvent.OnClickFavourite -> {
                toggleFavouriteDogBreed(event.breed)
            }
        }
    }

    private fun getDogBreeds() {
        _uiState.value = _uiState.value.copy(
            breeds = emptyList(),
            isLoading = true,
        )
        viewModelScope.launch {
            getAllBreedsUseCase().collect { result ->
                if (result.isEmpty()) {
                    _uiState.value = _uiState.value.copy(
                        breeds = emptyList(),
                        message = "No dog breeds found",
                        isLoading = false,
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        breeds = result,
                        message = null,
                        isLoading = false,
                    )
                }
            }
        }
    }

    private fun refreshDogBreeds() {
        viewModelScope.launch {
            refreshDogBreedsUseCase()
        }
    }

    private fun toggleFavouriteDogBreed(breed: DogBreed) {
        viewModelScope.launch {
            if (breed.isFavourite) {
                when (deleteFavouriteDogBreedUseCase(breed.id!!)) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            message = "Removed from favourites",
                            isLoading = false,
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            message = "An unexpected error occurred",
                            isLoading = false,
                        )
                    }

                    else -> {}
                }
            }
            if (!breed.isFavourite) {
                when (insertFavouriteDogBreedUseCase(breed.toFavouriteDogBreed())) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            message = "Added to favourites",
                            isLoading = false,
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            message = "An unexpected error occurred",
                            isLoading = false,
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(
            message = null,
        )
    }
}
