package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.dto.GetAllBreedsResponse
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.dto.GetBreedImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("api/breeds/list/all")
    suspend fun getAllDogBreeds(): Response<GetAllBreedsResponse>

    @GET("api/breed/{breed_name}/images")
    suspend fun getDogBreedImages(@Path("breed_name") breed: String): Response<GetBreedImagesResponse>
}
