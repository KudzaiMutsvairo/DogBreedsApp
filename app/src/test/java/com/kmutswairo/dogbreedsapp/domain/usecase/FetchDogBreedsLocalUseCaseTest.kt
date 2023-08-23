package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.DogBreedsRepository
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.usecase.FetchDogBreedsLocalUseCase
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchDogBreedsLocalUseCaseTest {

    private lateinit var useCase: FetchDogBreedsLocalUseCase
    private var repository: DogBreedsRepository = mockk<DogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = FetchDogBreedsLocalUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN getAllDogBreedsFromCache is called, WHEN there are dog breeds in the cache, THEN return list of dog breeds`() =
        runTest {
            // Arrange
            val expectedDogBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "Pug",
                    subBreeds = emptyList(),
                ),
                DogBreed(
                    id = 2,
                    name = "Labrador",
                    subBreeds = emptyList(),
                ),
            )
            coEvery { repository.getAllDogBreedsFromCache() } returns flow {
                emit(expectedDogBreeds)
            }

            // Act
            val resultFlow = useCase()
            val result = resultFlow.toList()

            // Assert
            assertEquals(expectedDogBreeds, result.first())
        }

    @Test
    fun `GIVEN getAllDogBreedsFromCache is called, WHEN there are no dog breeds in the cache, THEN return empty list`() =
        runTest {
            // Arrange
            val expectedDogBreeds = emptyList<DogBreed>()
            coEvery { repository.getAllDogBreedsFromCache() } returns flow {
                emit(expectedDogBreeds)
            }

            // Act
            val resultFlow = useCase()
            val result = resultFlow.toList()

            // Assert
            assertEquals(expectedDogBreeds, result.first())
        }
}
