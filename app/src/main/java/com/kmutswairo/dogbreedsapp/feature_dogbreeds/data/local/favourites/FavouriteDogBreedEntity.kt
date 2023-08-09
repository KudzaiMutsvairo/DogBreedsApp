package com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.local.favourites

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.SubBreedsConverter
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Constants

@Entity(tableName = Constants.TABLE_FAVORITE_BREEDS)
data class FavouriteDogBreedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var breedName: String,
    @TypeConverters(SubBreedsConverter::class)
    var subBreeds: List<String>,
)
