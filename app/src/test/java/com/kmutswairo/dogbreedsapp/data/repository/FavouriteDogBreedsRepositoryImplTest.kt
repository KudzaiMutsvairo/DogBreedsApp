package com.kmutswairo.dogbreedsapp.data.repository

import com.kmutswairo.dogbreedsapp.data.local.favourites.FavDogBreedsDao
import com.kmutswairo.dogbreedsapp.data.local.favourites.FavouriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.data.mapper.toFavoriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FavouriteDogBreedsRepositoryImplTest {
    private lateinit var repository: FavouriteDogBreedsRepositoryImpl
    private val favouriteDogBreedsDao: FavDogBreedsDao = mockk<FavDogBreedsDao>()

    @Before
    fun setUp() {
        repository = FavouriteDogBreedsRepositoryImpl(
            favouriteDogBreedsDao,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given insertFavouriteDogBreed is called with a dog breed, WHEN the dog breed is inserted successfully, THEN return success`() =
        runTest {
            // Arrange
            val dogBreed = FavouriteDogBreed(
                id = 1,
                name = "Breed",
                subBreeds = emptyList(),
            )
            val expectedDogBreedEntity = dogBreed.toFavoriteDogBreedEntity()

            coEvery { favouriteDogBreedsDao.insertFavDogBreed(expectedDogBreedEntity) } returns 1L

            // Act
            val result = repository.insertFavouriteDogBreed(dogBreed)

            // Assert
            assert(result is Resource.Success)
            assertEquals(1L, result.data)
        }

    @Test
    fun `Given insertFavouriteDogBreed is called with a dog breed, WHEN the dog breed is not inserted successfully, THEN return error`() =
        runTest {
            // Arrange
            val dogBreed = FavouriteDogBreed(
                id = 1,
                name = "Breed",
                subBreeds = emptyList(),
            )
            val expectedDogBreedEntity = dogBreed.toFavoriteDogBreedEntity()
            val expectedError = RuntimeException("Error getting favourite dog breeds")

            coEvery { favouriteDogBreedsDao.insertFavDogBreed(expectedDogBreedEntity) } throws expectedError

            // Act
            val result = repository.insertFavouriteDogBreed(dogBreed)

            // Assert
            assert(result is Resource.Error)
        }

    @Test
    fun `Given deleteFavouriteDogBreed is called with a dog breed, WHEN the dog breed is deleted successfully, THEN return success`() =
        runTest {
            // Arrange
            val dogBreed = FavouriteDogBreed(
                id = 1,
                name = "Breed",
                subBreeds = emptyList(),
            )
            val expectedDogBreedEntity = dogBreed.toFavoriteDogBreedEntity()

            coEvery { favouriteDogBreedsDao.deleteFavDogBreed(dogBreed.id) } returns 1

            // Act
            val result = repository.deleteFavouriteDogBreed(dogBreed.id)

            // Assert
            assert(result is Resource.Success)
        }

    @Test
    fun `Given deleteFavouriteDogBreed throws an exception THEN return Error`() = runTest {
        // Arrange
        val dogBreedId = 1
        val expectedError = RuntimeException("Error removing favourite dog breeds")

        coEvery { favouriteDogBreedsDao.deleteFavDogBreed(dogBreedId) } throws expectedError

        // Act
        val result = repository.deleteFavouriteDogBreed(dogBreedId)

        // Assert
        assert(result is Resource.Error)
    }

    @Test
    fun `Given getAllFavouriteDogBreeds is called, WHEN there are no favourite dog breeds in the cache, THEN return empty list`() =
        runTest { // Arrange
            val expectedDogBreeds = emptyList<FavouriteDogBreed>()
            val expectedDogBreedsEntityList = emptyList<FavouriteDogBreedEntity>()
            coEvery { favouriteDogBreedsDao.getAllFavDogBreeds() } returns expectedDogBreedsEntityList

            // Act
            val resultFlow = repository.getAllFavouriteDogBreeds()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Success)
            assertEquals(expectedDogBreeds, resultList.last().data)
        }

    @Test
    fun `Given getAllFavouriteDogBreeds is called, WHEN there are favourite dog breeds in the cache, THEN return FavouriteDogBreeds list`() =
        runTest { // Arrange
            val expectedDogBreeds = listOf<FavouriteDogBreed>(
                FavouriteDogBreed(
                    id = 1,
                    name = "affenpinscher",
                    subBreeds = listOf("SubBreed1", "SubBreed2"),
                ),
                FavouriteDogBreed(
                    id = 2,
                    name = "african",
                    subBreeds = emptyList(),
                ),
            )
            val expectedDogBreedsEntityList = expectedDogBreeds.map {
                FavouriteDogBreedEntity(
                    id = it.id,
                    breedName = it.name,
                    subBreeds = it.subBreeds,
                )
            }
            coEvery { favouriteDogBreedsDao.getAllFavDogBreeds() } returns expectedDogBreedsEntityList

            // Act
            val resultFlow = repository.getAllFavouriteDogBreeds()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Success)
            assertEquals(expectedDogBreeds, resultList.last().data)
        }

    @Test
    fun `Given getAllFavouriteDogBreeds throws an Exception, THEN return Error`() = runTest {
        // Arrange
        val expectedError = RuntimeException("Error getting favourite dog breeds")
        coEvery { favouriteDogBreedsDao.getAllFavDogBreeds() } throws expectedError

        // Act
        val resultFlow = repository.getAllFavouriteDogBreeds()
        val resultList = resultFlow.toList()

        // Assert
        assertTrue(resultList.first() is Resource.Loading)
        assertTrue(resultList.last() is Resource.Error)
    }
}
