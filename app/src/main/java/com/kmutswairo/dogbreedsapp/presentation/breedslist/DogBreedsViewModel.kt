package com.kmutswairo.dogbreedsapp.presentation.breedslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmutswairo.dogbreedsapp.data.mapper.toFavouriteDogBreed
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.domain.usecase.DeleteFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.domain.usecase.GetAllBreedsUseCase
import com.kmutswairo.dogbreedsapp.domain.usecase.InsertFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.domain.usecase.RefreshDogBreedsUseCase
import com.kmutswairo.dogbreedsapp.presentation.breedslist.events.DogBreedsListEvent
import com.kmutswairo.dogbreedsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    val uiState = _uiState

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
            message = null,
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
                    return@collect
                }
                _uiState.value = _uiState.value.copy(
                    breeds = result,
                    message = null,
                    isLoading = false,
                )
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
            } else {
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
}
