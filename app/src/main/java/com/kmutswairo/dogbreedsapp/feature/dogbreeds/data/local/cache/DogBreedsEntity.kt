package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.SubBreedsConverter
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Constants

@Entity(tableName = Constants.TABLE_DOG_BREEDS)
data class DogBreedsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    @TypeConverters(SubBreedsConverter::class)
    var subBreeds: List<String>,
)
