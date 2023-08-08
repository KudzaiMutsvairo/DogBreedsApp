package com.kmutswairo.dogbreedsapp.data.local.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.util.Constants

@Dao
interface DogBreedsDao {
    @Insert
    suspend fun insertDogBreeds(breeds: List<DogBreedsEntity>): List<Long>

    @Query(
        "SELECT dog_breeds.*, favorite_breeds.breedName IS NOT NULL as isFavourite FROM " +
            "${Constants.TABLE_DOG_BREEDS} LEFT JOIN ${Constants.TABLE_FAVORITE_BREEDS} " +
            "ON dog_breeds.name = favorite_breeds.breedName",
    )
    suspend fun getAllDogBreeds(): List<DogBreed>

    @Query("DELETE FROM ${Constants.TABLE_DOG_BREEDS}")
    suspend fun deleteAllDogBreeds(): Int
}
