package com.kmutswairo.dogbreedsapp.data.repository

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.DogApi
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.remote.dto.GetBreedImagesResponse
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.data.repository.ViewBreedRepositoryImpl
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ViewBreedRepositoryImplTest {

    private lateinit var repository: ViewBreedRepositoryImpl
    private val mockViewBreedApi: DogApi = mockk<DogApi>()

    @Before
    fun setUp() {
        repository = ViewBreedRepositoryImpl(mockViewBreedApi)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `GIVEN a valid breed name, WHEN fetching breed images, RETURN success resource`() =
        runTest {
            // Arrange
            val breed = "affenpinscher"
            val expectedImages = listOf("image1.jpg", "image2.jpg")
            val mockResponse = mockk<Response<GetBreedImagesResponse>>(relaxed = true)
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.body() } returns GetBreedImagesResponse(
                message = expectedImages,
                status = "success",
            )

            coEvery { mockViewBreedApi.getDogBreedImages(breed) } returns mockResponse

            // Act
            val resultFlow = repository.getBreedImages(breed)
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Success)
        }

    @Test
    fun `Given a valid name of a breed, WHEN fetching breed images, RETURN error resource`() =
        runTest {
            // Arrange
            val breed = "affenpinscher"
            val expectedImages = emptyList<String>()
            val mockResponse = mockk<Response<GetBreedImagesResponse>>(relaxed = true)
            every { mockResponse.isSuccessful } returns false
            every { mockResponse.body() } returns GetBreedImagesResponse(
                message = expectedImages,
                status = "failed",
            )

            coEvery { mockViewBreedApi.getDogBreedImages(breed) } returns mockResponse

            // Act
            val resultFlow = repository.getBreedImages(breed)
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Error)
        }
}
