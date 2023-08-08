package com.kmutswairo.dogbreedsapp.presentation.favouritebreed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmutswairo.dogbreedsapp.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.domain.usecase.DeleteFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.domain.usecase.GetAllFavouriteDogBreedUseCase
import com.kmutswairo.dogbreedsapp.presentation.favouritebreed.events.FavouriteBreedsEvent
import com.kmutswairo.dogbreedsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteDogBreedsViewModel @Inject constructor(
    private val getAllFavouriteDogBreedUseCase: GetAllFavouriteDogBreedUseCase,
    private val deleteFavouriteDogBreedUseCase: DeleteFavouriteDogBreedUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteDogBreedsState())
    val uiState = _uiState

    fun onEvent(event: FavouriteBreedsEvent) {
        when (event) {
            is FavouriteBreedsEvent.RemoveFavouriteBreed -> {
                deleteFavouriteDogBreed(event.breed)
            }
        }
    }

    init {
        getFavouriteDogBreeds()
    }

    private fun getFavouriteDogBreeds() {
        viewModelScope.launch {
            getAllFavouriteDogBreedUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { breedsList ->
                            _uiState.value = _uiState.value.copy(
                                breeds = breedsList,
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            message = result.message,
                            isLoading = false,
                        )
                    }

                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }

    private fun deleteFavouriteDogBreed(breed: FavouriteDogBreed) {
        viewModelScope.launch {
            deleteFavouriteDogBreedUseCase(breed.id).let { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            message = "Successfully removed ${breed.name} from favourites",
                            isLoading = false,
                        )
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            message = result.message,
                            isLoading = false,
                        )
                    }

                    else -> {}
                }
            }
            getFavouriteDogBreeds()
        }
    }
}
