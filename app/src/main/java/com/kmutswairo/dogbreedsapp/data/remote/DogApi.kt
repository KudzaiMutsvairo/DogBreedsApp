package com.kmutswairo.dogbreedsapp.data.remote

import com.kmutswairo.dogbreedsapp.data.remote.dto.GetAllBreedsResponse
import com.kmutswairo.dogbreedsapp.data.remote.dto.GetBreedImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    @GET("api/breeds/list/all")
    suspend fun getAllDogBreeds(): GetAllBreedsResponse

    @GET("api/breed/{breed_name}/images")
    suspend fun getDogBreedImages(@Path("breed_name") breed: String): GetBreedImagesResponse
}