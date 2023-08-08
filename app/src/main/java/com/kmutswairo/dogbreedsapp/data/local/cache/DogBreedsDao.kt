package com.kmutswairo.dogbreedsapp.data.local.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kmutswairo.dogbreedsapp.util.Constants

@Dao
interface DogBreedsDao {
    @Insert
    suspend fun insertDogBreeds(breeds: List<DogBreedsEntity>): List<Long>

    @Query(
        "SELECT dog_breeds.*, favorite_breeds.breedName IS NOT NULL as isFavorite FROM " +
            "${Constants.TABLE_DOG_BREEDS} LEFT JOIN ${Constants.TABLE_FAVORITE_BREEDS} " +
            "ON dog_breeds.breedName = favorite_breeds.breedName",
    )
    suspend fun getAllDogBreeds(): List<DogBreedsEntity>

    @Query("DELETE FROM ${Constants.TABLE_DOG_BREEDS}")
    suspend fun deleteAllDogBreeds(): Int
}
