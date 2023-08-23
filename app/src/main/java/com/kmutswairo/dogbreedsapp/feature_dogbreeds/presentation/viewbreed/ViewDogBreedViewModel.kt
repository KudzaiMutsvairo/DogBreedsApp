package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.usecase.GetBreedImagesUseCase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.viewbreed.events.ViewDogBreedEvent
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewDogBreedViewModel @Inject constructor(
    private val getBreedImagesUseCase: GetBreedImagesUseCase,
) : ViewModel() {
    private var _uiState: MutableStateFlow<ViewDogBreedState> =
        MutableStateFlow(ViewDogBreedState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ViewDogBreedState(
            isLoading = true,
            dogBreeds = emptyList(),
            message = null,
        ),
    )

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
