package com.kmutswairo.dogbreedsapp.data.local.favourites

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.local.DogBreedsDatabase
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.local.favourites.FavDogBreedsDao
import com.kmutswairo.dogbreedsapp.feature_dogbreeds.data.local.favourites.FavouriteDogBreedEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class FavDogBreedsDaoTest {

    private lateinit var favDogBreedDao: FavDogBreedsDao
    private lateinit var database: DogBreedsDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            DogBreedsDatabase::class.java,
        ).allowMainThreadQueries().build()
        favDogBreedDao = database.favDogBreedsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val favDogBreed1 = FavouriteDogBreedEntity(
        breedName = "breed1",
        subBreeds = listOf("subBreed1", "subBreed2"),
    )

    val favDogBreed2 = FavouriteDogBreedEntity(
        breedName = "breed2",
        subBreeds = listOf("subBreed1", "subBreed2"),
    )

    @Test
    fun insertFavDogBreeds() = runTest {
        val result = favDogBreedDao.insertFavDogBreed(favDogBreed1)
        assert(result == 1L)
    }

    @Test
    fun deleteFavDogBreed() = runTest {
        favDogBreedDao.insertFavDogBreed(favDogBreed1)
        val favBreedsInDb = favDogBreedDao.getAllFavDogBreeds()
        val result = favBreedsInDb[0].id?.let { favDogBreedDao.deleteFavDogBreed(it) }
        assert(result == 1)
    }

    @Test
    fun getAllFavDogBreeds() = runTest {
        favDogBreedDao.insertFavDogBreed(favDogBreed1)
        favDogBreedDao.insertFavDogBreed(favDogBreed2)
        val result = favDogBreedDao.getAllFavDogBreeds()
        assert(result.size == 2)
    }
}
