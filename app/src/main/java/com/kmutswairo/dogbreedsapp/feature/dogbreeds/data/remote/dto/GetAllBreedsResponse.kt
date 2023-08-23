package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.dto

data class GetAllBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String,
)
