package com.kmutswairo.dogbreedsapp.domain.usecase

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
    fun `Given deleteAllDogBreeds is called, WHEN there are dog breeds in the cache, THEN return true`() =
        runTest {
            // Arrange
            coEvery { repository.deleteAllDogBreeds() } returns true

            // Act
            val result = useCase()

            // Assert
            assertTrue(result)
        }

    @Test
    fun `Given deleteAllDogBreeds is called, WHEN there are no dog breeds in the cache, THEN return false`() =
        runTest {
            // Arrange
            coEvery { repository.deleteAllDogBreeds() } returns false

            // Act
            val result = useCase()

            // Assert
            assertFalse(result)
        }
}
