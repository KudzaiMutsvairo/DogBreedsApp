package com.kmutswairo.dogbreedsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kmutswairo.dogbreedsapp.data.SubBreedsConverter
import com.kmutswairo.dogbreedsapp.data.local.cache.DogBreedsDao
import com.kmutswairo.dogbreedsapp.data.local.cache.DogBreedsEntity
import com.kmutswairo.dogbreedsapp.data.local.favourites.FavDogBreedsDao
import com.kmutswairo.dogbreedsapp.data.local.favourites.FavouriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.util.Constants

@Database(entities = [DogBreedsEntity::class, FavouriteDogBreedEntity::class], version = Constants.DATABASE_VERSION, exportSchema = false)
@TypeConverters(SubBreedsConverter::class)
abstract class DogBreedsDatabase : RoomDatabase() {
    abstract fun dogBreedsDao(): DogBreedsDao
    abstract fun favDogBreedsDao(): FavDogBreedsDao
}
