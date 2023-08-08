package com.kmutswairo.dogbreedsapp.data.local.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.kmutswairo.dogbreedsapp.data.local.DogBreedsDatabase
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@SmallTest
class DogBreedDaoTest {

    private lateinit var dogBreedDao: DogBreedsDao
    private lateinit var database: DogBreedsDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            DogBreedsDatabase::class.java,
        ).allowMainThreadQueries().build()
        dogBreedDao = database.dogBreedsDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    val dogBreed1 = DogBreedsEntity(
        name = "breed1",
        subBreeds = listOf("subBreed1", "subBreed2"),
    )

    val dogBreed2 = DogBreedsEntity(
        name = "breed2",
        subBreeds = listOf("subBreed1", "subBreed2"),
    )

    @Test
    fun insertDogBreeds() = runTest {
        val result = dogBreedDao.insertDogBreeds(listOf(dogBreed1, dogBreed2))
        assert(result.size == 2)
    }

    @Test
    fun getDogBreeds() = runTest {
        dogBreedDao.insertDogBreeds(listOf(dogBreed1, dogBreed2))
        val resultFlow = dogBreedDao.getAllDogBreeds()
        val result = resultFlow.toList()
        assert(result.size == 2)
    }

    @Test
    fun deleteDogBreeds() = runTest {
        dogBreedDao.insertDogBreeds(listOf(dogBreed1, dogBreed2))
        val result = dogBreedDao.deleteAllDogBreeds()
        assert(result == 2)
    }

    @Test
    fun insertSingleDogBreed() = runTest {
        val result = dogBreedDao.insertSingleDogBreed(dogBreed1)
        assert(result == 1L)
    }
}
