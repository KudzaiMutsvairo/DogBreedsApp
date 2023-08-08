package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllBreedsUseCaseTest {
    private var fetchRemoteUseCaseMock: FetchRemoteDogBreedsUseCase =
        mockk<FetchRemoteDogBreedsUseCase>()
    private var fetchLocalUseCaseMock: FetchDogBreedsLocalUseCase =
        mockk<FetchDogBreedsLocalUseCase>()
    private var saveBreedsUseCaseMock: SaveDogBreedsToCacheUseCase =
        mockk<SaveDogBreedsToCacheUseCase>()

    private lateinit var getAllBreedsUseCase: GetAllBreedsUseCase

    @Before
    fun setUp() {
        getAllBreedsUseCase = GetAllBreedsUseCase(
            fetchRemoteDogBreedsUseCase = fetchRemoteUseCaseMock,
            fetchDogBreedsLocalUseCase = fetchLocalUseCaseMock,
            saveDogBreedsToCacheUseCase = saveBreedsUseCaseMock,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN there are saved DogBreeds and fetchLocalDogBreeds is successful THEN return success`() =
        runTest {
            // Arrange
            val expectedBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "Breed 1",
                    subBreeds = listOf("Sub Breed 1", "Sub Breed 2"),
                ),
                DogBreed(
                    id = 2,
                    name = "Breed 2",
                    subBreeds = listOf("Sub Breed 1", "Sub Breed 2"),
                ),
            )
            coEvery { fetchLocalUseCaseMock() } returns flow {
                emit(expectedBreeds)
            }

            // Act
            val actualBreedsFlow = getAllBreedsUseCase()
            val actualBreeds = actualBreedsFlow.toList()

            // Assert
            assertEquals(expectedBreeds, actualBreeds[0])
        }

    @Test
    fun `GIVEN there are no saved DogBreeds and fetchRemoteDogBreeds is successful THEN return success`() =
        runTest {
            // Arrange
            val expectedBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "Breed 1",
                    subBreeds = listOf("Sub Breed 1", "Sub Breed 2"),
                ),
                DogBreed(
                    id = 2,
                    name = "Breed 2",
                    subBreeds = listOf("Sub Breed 1", "Sub Breed 2"),
                ),
            )
            coEvery { fetchLocalUseCaseMock() } returns flow {
                emit(emptyList<DogBreed>())
            }
            coEvery { fetchRemoteUseCaseMock() } returns flow {
                emit(Resource.Success(expectedBreeds))
            }
            coEvery { saveBreedsUseCaseMock(expectedBreeds) } returns true

            // Act
            val actualBreedsFlow = getAllBreedsUseCase()

            // Assert
            coVerify { fetchLocalUseCaseMock() }
            coVerify { fetchRemoteUseCaseMock() }
            coVerify { saveBreedsUseCaseMock(expectedBreeds) }
        }
}
