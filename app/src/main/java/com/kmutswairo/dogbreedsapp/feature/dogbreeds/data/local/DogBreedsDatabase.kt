package com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.SubBreedsConverter
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.cache.DogBreedsDao
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.cache.DogBreedsEntity
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.favourites.FavDogBreedsDao
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.local.favourites.FavouriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Constants

@Database(entities = [DogBreedsEntity::class, FavouriteDogBreedEntity::class], version = Constants.DATABASE_VERSION, exportSchema = false)
@TypeConverters(SubBreedsConverter::class)
abstract class DogBreedsDatabase : RoomDatabase() {
    abstract fun dogBreedsDao(): DogBreedsDao
    abstract fun favDogBreedsDao(): FavDogBreedsDao
}
