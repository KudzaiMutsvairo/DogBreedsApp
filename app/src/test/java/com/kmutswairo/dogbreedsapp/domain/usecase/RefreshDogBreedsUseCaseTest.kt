package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class RefreshDogBreedsUseCaseTest {
    private val fetchRemoteDogBreedsUseCase: FetchRemoteDogBreedsUseCase =
        mockk<FetchRemoteDogBreedsUseCase>()
    private val deleteAllDogBreedsUseCase: DeleteAllDogBreedsUseCase =
        mockk<DeleteAllDogBreedsUseCase>()
    private val saveDogBreedsToCacheUseCase: SaveDogBreedsToCacheUseCase =
        mockk<SaveDogBreedsToCacheUseCase>()

    private lateinit var refreshDogBreedsUseCase: RefreshDogBreedsUseCase

    @Before
    fun setUp() {
        refreshDogBreedsUseCase = RefreshDogBreedsUseCase(
            fetchRemoteDogBreedsUseCase,
            deleteAllDogBreedsUseCase,
            saveDogBreedsToCacheUseCase,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN deleteAllDogBreedsUseCase returns true WHEN invoke is called THEN return Resource_Success`() =
        runTest {
            // Arrange
            val expectedBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "Breed 1",
                    subBreeds = emptyList(),
                ),
                DogBreed(
                    id = 2,
                    name = "Breed 2",
                    subBreeds = emptyList(),
                ),
            )
            val expected = true
            coEvery { deleteAllDogBreedsUseCase() } returns expected
            coEvery { fetchRemoteDogBreedsUseCase() } returns flow {
                emit(Resource.Loading())
                emit(Resource.Success(data = expectedBreeds))
            }
            coEvery { saveDogBreedsToCacheUseCase(any()) } returns expected

            // Act
            val actualFlow = refreshDogBreedsUseCase()
            val results = actualFlow.toList()

            // Assert
            assert(results.first() is Resource.Loading)
            assert(results[1] is Resource.Success)
        }

    @Test
    fun `GIVEN deleteAllDogBreedsUseCase returns false WHEN invoke is called THEN return Resource_Error`() =
        runTest {
            // Arrange
            val expected = false
            coEvery { deleteAllDogBreedsUseCase() } returns expected

            // Act
            val actualFlow = refreshDogBreedsUseCase()
            val results = actualFlow.toList()

            // Assert
            assert(results[0] is Resource.Error)
        }

    @Test
    fun `GIVEN fetchRemoteDogBreedsUseCase returns Resource_Error WHEN invoke is called THEN return Resource_Error`() =
        runTest {
            // Arrange
            coEvery { deleteAllDogBreedsUseCase() } returns true
            coEvery { fetchRemoteDogBreedsUseCase() } returns flow {
                emit(Resource.Loading())
                emit(Resource.Error("Error"))
            }

            // Act
            val actualFlow = refreshDogBreedsUseCase()
            val results = actualFlow.toList()

            // Assert
            assert(results[0] is Resource.Loading)
            assert(results[1] is Resource.Error)
        }

    @Test
    fun `GIVEN saveDogBreedsToCacheUseCase returns false WHEN invoke is called THEN return Resource_Error`() =
        runTest {
            // Arrange
            val expectedBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "Breed 1",
                    subBreeds = emptyList(),
                ),
                DogBreed(
                    id = 2,
                    name = "Breed 2",
                    subBreeds = emptyList(),
                ),
            )
            val expected = false
            coEvery { deleteAllDogBreedsUseCase() } returns true
            coEvery { fetchRemoteDogBreedsUseCase() } returns flow {
                emit(Resource.Loading())
                emit(Resource.Success(data = expectedBreeds))
            }
            coEvery { saveDogBreedsToCacheUseCase(any()) } returns expected

            // Act
            val actualFlow = refreshDogBreedsUseCase()
            val results = actualFlow.toList()

            // Assert
            assert(results[0] is Resource.Loading)
            assert(results[1] is Resource.Error)
        }
}
