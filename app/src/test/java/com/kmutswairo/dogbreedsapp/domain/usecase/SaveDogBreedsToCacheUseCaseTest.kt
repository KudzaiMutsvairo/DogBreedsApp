package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SaveDogBreedsToCacheUseCaseTest {
    private lateinit var useCase: SaveDogBreedsToCacheUseCase
    private var repository: DogBreedsRepository = mockk<DogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = SaveDogBreedsToCacheUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN valid dog breeds, WHEN saveDogBreedsToCache is called, THEN return Success`() =
        runTest {
            // Arrange
            val dogBreeds = listOf(
                DogBreed(
                    name = "Poodle",
                    subBreeds = emptyList(),
                ),
            )
            coEvery { repository.saveDogBreedsToCache(dogBreeds) } returns true
            // Act
            val result = useCase(dogBreeds)

            // Assert
            assertTrue(result)
        }

    @Test
    fun `GIVEN invalid dog breeds, WHEN saveDogBreedsToCache is called, THEN return Error`() =
        runTest {
            // Arrange
            val dogBreeds = emptyList<DogBreed>()
            coEvery { repository.saveDogBreedsToCache(dogBreeds) } returns false

            // Act
            val result = useCase(dogBreeds)

            // Assert
            assertFalse(result)
        }
}
