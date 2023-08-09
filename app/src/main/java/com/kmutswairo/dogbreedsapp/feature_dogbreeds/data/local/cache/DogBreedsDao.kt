package com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.local.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface DogBreedsDao {
    @Insert
    suspend fun insertDogBreeds(breeds: List<DogBreedsEntity>): List<Long>

    @Insert
    suspend fun insertSingleDogBreed(breed: DogBreedsEntity): Long

    @Query(
        "SELECT dog_breeds.*, favorite_breeds.breedName IS NOT NULL as isFavourite FROM " +
            "${Constants.TABLE_DOG_BREEDS} LEFT JOIN ${Constants.TABLE_FAVORITE_BREEDS} " +
            "ON dog_breeds.name = favorite_breeds.breedName",
    )
    fun getAllDogBreeds(): Flow<List<DogBreed>>

    @Query("DELETE FROM ${Constants.TABLE_DOG_BREEDS}")
    suspend fun deleteAllDogBreeds(): Int
}
