package com.kmutswairo.dogbreedsapp.data

import androidx.room.TypeConverter

class SubBreedsConverter {
    @TypeConverter
    fun fromList(subBreeds: List<String>): String {
        return subBreeds.joinToString(", ")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        return if (data.isNotEmpty()) data.split(", ") else emptyList()
    }
}
