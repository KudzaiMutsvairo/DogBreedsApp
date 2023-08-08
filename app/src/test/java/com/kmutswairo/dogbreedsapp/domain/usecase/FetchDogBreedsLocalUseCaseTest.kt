package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
                emit(Resource.Loading())
                emit(Resource.Success(expectedDogBreeds))
            }

            // Act
            val resultFlow = useCase()
            val result = resultFlow.toList()

            // Assert
            assertTrue(result.first() is Resource.Loading)
            assertTrue(result.last() is Resource.Success)
            assertEquals(expectedDogBreeds, (result.last() as Resource.Success).data)
        }

    @Test
    fun `GIVEN getAllDogBreedsFromCache is called, WHEN there are no dog breeds in the cache, THEN return empty list`() =
        runTest {
            // Arrange
            val expectedDogBreeds = emptyList<DogBreed>()
            coEvery { repository.getAllDogBreedsFromCache() } returns flow {
                emit(Resource.Loading())
                emit(Resource.Success(expectedDogBreeds))
            }

            // Act
            val resultFlow = useCase()
            val result = resultFlow.toList()

            // Assert
            assertTrue(result.first() is Resource.Loading)
            assertTrue(result.last() is Resource.Success)
            assertEquals(expectedDogBreeds, (result.last() as Resource.Success).data)
        }

    @Test
    fun `GIVEN getAllDogBreedsFromCache is called, WHEN there is an error, THEN return error`() =
        runTest {
            // Arrange
            val expectedErrorMessage = "Error getting dog breeds from cache"
            coEvery { repository.getAllDogBreedsFromCache() } returns flow {
                emit(Resource.Loading())
                emit(Resource.Error(message = expectedErrorMessage))
            }

            // Act
            val resultFlow = useCase()
            val result = resultFlow.toList()

            // Assert
            assertTrue(result.first() is Resource.Loading)
            assertTrue(result.last() is Resource.Error)
            assertEquals(expectedErrorMessage, (result.last() as Resource.Error).message)
        }
}
