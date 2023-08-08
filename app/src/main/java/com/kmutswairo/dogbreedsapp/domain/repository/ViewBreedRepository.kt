package com.kmutswairo.dogbreedsapp.domain.repository

import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ViewBreedRepository {
    suspend fun getBreedImages(breed: String): Flow<Resource<List<String>>>
}
