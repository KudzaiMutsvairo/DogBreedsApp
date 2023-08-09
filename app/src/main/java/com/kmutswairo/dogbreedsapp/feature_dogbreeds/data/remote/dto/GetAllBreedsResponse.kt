package com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.remote.dto

data class GetAllBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String,
)
