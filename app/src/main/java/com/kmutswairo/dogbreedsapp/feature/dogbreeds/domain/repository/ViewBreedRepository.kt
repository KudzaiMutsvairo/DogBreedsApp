package com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Resource
import kotlinx.coroutines.flow.Flow

interface ViewBreedRepository {
    suspend fun getBreedImages(breed: String): Flow<Resource<List<String>>>
}
