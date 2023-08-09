package com.kmutswairo.dogbreedsapp.presentation.viewbreed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmutswairo.dogbreedsapp.domain.usecase.GetBreedImagesUseCase
import com.kmutswairo.dogbreedsapp.presentation.viewbreed.events.ViewDogBreedEvent
import com.kmutswairo.dogbreedsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewDogBreedViewModel @Inject constructor(
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<ViewDogBreedState> =
        MutableStateFlow(ViewDogBreedState())
    val uiState = _uiState

    fun onEvent(event: ViewDogBreedEvent) {
        when (event) {
            is ViewDogBreedEvent.Load -> {
                getBreedImages(event.breedName)
            }
        }
    }

    private fun getBreedImages(breed: String) {
        viewModelScope.launch {
            getBreedImagesUseCase(breed).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { images ->
                            _uiState.value = _uiState.value.copy(
                                dogBreeds = images,
                                message = null,
                                isLoading = false,
                            )
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            message = "Error getting breed images",
                            isLoading = false,
                            dogBreeds = emptyList(),
                        )
                    }

                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            message = null,
                            dogBreeds = emptyList(),
                        )
                    }
                }
            }
        }
    }
}