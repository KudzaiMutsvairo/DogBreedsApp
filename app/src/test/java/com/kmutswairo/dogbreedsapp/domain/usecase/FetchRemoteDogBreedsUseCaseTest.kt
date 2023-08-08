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

class FetchRemoteDogBreedsUseCaseTest {
    private lateinit var useCase: FetchRemoteDogBreedsUseCase
    private var repository: DogBreedsRepository = mockk<DogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = FetchRemoteDogBreedsUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN fetchRemoteDogBreeds is successful THEN return success`() = runTest {
        // Arrange
        val expectedBreeds = listOf(
            DogBreed(
                id = null,
                name = "Pug",
                subBreeds = emptyList(),
            ),
            DogBreed(
                id = null,
                name = "Labrador",
                subBreeds = emptyList(),
            ),
        )

        coEvery { repository.fetchRemoteDogBreeds() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(expectedBreeds))
        }
        // Act
        val resultFlow = useCase()
        val result = resultFlow.toList()

        // Assert
        assertTrue(result.first() is Resource.Loading)
        assertTrue(result.last() is Resource.Success)
        assertEquals(expectedBreeds, (result.last() as Resource.Success).data)
    }

    @Test
    fun `GIVEN fetchRemoteDogBreeds is unsuccessful THEN return error`() = runTest {
        // Arrange
        val expectedErrorMessage = "Error fetching remote dog breeds"

        coEvery { repository.fetchRemoteDogBreeds() } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(expectedErrorMessage))
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
