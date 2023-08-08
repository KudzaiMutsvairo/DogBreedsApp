package com.kmutswairo.dogbreedsapp.data.local.favourites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kmutswairo.dogbreedsapp.util.Constants

@Dao
interface FavDogBreedsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavDogBreed(favouriteDogBreedEntity: FavouriteDogBreedEntity): Long

    @Query("DELETE FROM ${Constants.TABLE_FAVORITE_BREEDS} WHERE id = :id")
    suspend fun deleteFavDogBreed(id: Int): Int

    @Query("SELECT * FROM ${Constants.TABLE_FAVORITE_BREEDS}")
    suspend fun getAllFavDogBreeds(): List<FavouriteDogBreedEntity>
}
