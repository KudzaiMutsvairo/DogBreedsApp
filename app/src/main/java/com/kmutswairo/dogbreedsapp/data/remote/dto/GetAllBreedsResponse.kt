package com.kmutswairo.dogbreedsapp.data.remote.dto

data class GetAllBreedsResponse(
    val message: Map<String, List<String>>,
    val status: String,
)
