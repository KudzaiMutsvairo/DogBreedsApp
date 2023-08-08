package com.kmutswairo.dogbreedsapp.domain.usecase

import com.kmutswairo.dogbreedsapp.domain.repository.DogBreedsRepository
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DeleteAllDogBreedsUseCaseTest {

    private lateinit var useCase: DeleteAllDogBreedsUseCase
    private var repository: DogBreedsRepository = mockk<DogBreedsRepository>()

    @Before
    fun setUp() {
        useCase = DeleteAllDogBreedsUseCase(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given deleteAllDogBreeds is called, WHEN there are dog breeds in the cache, THEN return number of deleted dog breeds`() =
        runTest {
            // Arrange
            val expectedDeletedDogBreeds = 1
            coEvery { repository.deleteAllDogBreeds() } returns Resource.Success(
                expectedDeletedDogBreeds,
            )

            // Act
            val result = useCase()

            // Assert
            assertTrue(result is Resource.Success)
            assertEquals(expectedDeletedDogBreeds, result.data)
        }

    @Test
    fun `Given deleteAllDogBreeds is called, WHEN there are no dog breeds in the cache, THEN return zero`() =
        runTest {
            // Arrange
            val expectedDeletedDogBreeds = 0
            coEvery { repository.deleteAllDogBreeds() } returns Resource.Success(
                expectedDeletedDogBreeds,
            )

            // Act
            val result = useCase()

            // Assert
            assertTrue(result is Resource.Success)
            assertEquals(expectedDeletedDogBreeds, result.data)
        }

    @Test
    fun `Given deleteAllDogBreeds is called, WHEN there is an error, THEN return error`() =
        runTest {
            // Arrange
            val expectedError = "Error deleting dog breeds"
            coEvery { repository.deleteAllDogBreeds() } returns Resource.Error(expectedError)

            // Act
            val result = useCase()

            // Assert
            assertTrue(result is Resource.Error)
            assertEquals(expectedError, result.message)
        }
}
