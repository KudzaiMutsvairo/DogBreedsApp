package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.repository.ViewBreedRepository
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.usecase.GetBreedImagesUseCase
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Resource
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

class GetBreedImagesUseCaseTest {

    private lateinit var useCase: GetBreedImagesUseCase
    private var repository: ViewBreedRepository = mockk<ViewBreedRepository>()

    @Before
    fun setUp() {
        useCase = GetBreedImagesUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN getBreedImages is successful THEN return success`() = runTest {
        // Arrange
        val expectedImages = listOf(
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1003.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg",
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1023.jpg",
        )
        coEvery { repository.getBreedImages("hound-afghan") } returns flow {
            emit(Resource.Loading())
            emit(Resource.Success(expectedImages))
        }

        // Act
        val resultFlow = useCase("hound-afghan")
        val result = resultFlow.toList()

        // Assert
        assertTrue(result.first() is Resource.Loading)
        assertTrue(result.last() is Resource.Success)
        assertEquals(expectedImages, (result.last() as Resource.Success).data)
    }

    @Test
    fun `GIVEN getBreedImages is unsuccessful THEN return error`() = runTest {
        // Arrange
        val expectedError = "Error"
        coEvery { repository.getBreedImages("hound-afghan") } returns flow {
            emit(Resource.Loading())
            emit(Resource.Error(expectedError))
        }

        // Act
        val resultFlow = useCase("hound-afghan")
        val result = resultFlow.toList()

        // Assert
        assertTrue(result.first() is Resource.Loading)
        assertTrue(result.last() is Resource.Error)
        assertEquals(expectedError, (result.last() as Resource.Error).message)
    }
}
