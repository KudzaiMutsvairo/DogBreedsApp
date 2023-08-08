package com.kmutswairo.dogbreedsapp.data.repository

import com.kmutswairo.dogbreedsapp.data.local.cache.DogBreedsDao
import com.kmutswairo.dogbreedsapp.data.mapper.toDogBreedsEntity
import com.kmutswairo.dogbreedsapp.data.remote.DogApi
import com.kmutswairo.dogbreedsapp.data.remote.dto.GetAllBreedsResponse
import com.kmutswairo.dogbreedsapp.domain.model.DogBreed
import com.kmutswairo.dogbreedsapp.util.Resource
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.lang.RuntimeException

class DogBreedsRepositoryImplTest {
    private lateinit var repository: DogBreedsRepositoryImpl
    private val mockBreedApi: DogApi = mockk<DogApi>()
    private val dogBreedsDao: DogBreedsDao = mockk<DogBreedsDao>()

    @Before
    fun setUp() {
        repository = DogBreedsRepositoryImpl(
            mockBreedApi,
            dogBreedsDao,
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Given getAllDogBreedsFromCache is called, WHEN there are no dog breeds in the cache, THEN return empty list`() =
        runTest {
            // Arrange
            val expectedDogBreeds = emptyList<DogBreed>()
            coEvery { dogBreedsDao.getAllDogBreeds() } returns expectedDogBreeds

            // Act
            val resultFlow = repository.getAllDogBreedsFromCache()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Success)
        }

    @Test
    fun `Given getAllDogBreedsFromCache is called, WHEN there are dog breeds in the cache, THEN return dog breeds list`() =
        runTest {
            // Arrange
            val expectedDogBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "affenpinscher",
                    subBreeds = listOf("subBreed1", "SubBreed2"),
                ),
                DogBreed(
                    id = 2,
                    name = "african",
                    subBreeds = emptyList(),
                ),
            )
            coEvery { dogBreedsDao.getAllDogBreeds() } returns expectedDogBreeds

            // Act
            val resultFlow = repository.getAllDogBreedsFromCache()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Success)
        }

    @Test
    fun `Given getAllDogBreedsFromCache is called, WHEN there is an error fetching dog breeds from cache, THEN return error resource`() =
        runTest {
            // Arrange
            val expectedDogBreeds = emptyList<DogBreed>()
            coEvery { dogBreedsDao.getAllDogBreeds() } throws Exception(RuntimeException("Error fetching dog breeds from cache"))

            // Act
            val resultFlow = repository.getAllDogBreedsFromCache()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Error)
        }

    @Test
    fun `Given deleteAllDogBreeds is called THEN return number of deleted dog breeds`() = runTest {
        // Arrange
        coEvery { dogBreedsDao.deleteAllDogBreeds() } returns 2

        // Act
        val result = repository.deleteAllDogBreeds()

        // Assert
        assertEquals(2, result.data)
    }

    @Test
    fun `Given getAllDogBreeds is successful WHEN fetchRemoteDogBreeds is called THEN return Resource Success`() =
        runTest {
            // Arrange
            val expectedDogBreedsMap = mapOf(
                "affenpinscher" to listOf("subBreed1", "SubBreed2"),
                "african" to emptyList(),
            )
            val mockResponse = mockk<Response<GetAllBreedsResponse>>(relaxed = true)
            every { mockResponse.isSuccessful } returns true
            every { mockResponse.body() } returns GetAllBreedsResponse(
                message = expectedDogBreedsMap,
                status = "success",
            )
            coEvery { mockBreedApi.getAllDogBreeds() } returns mockResponse

            // Act
            val resultFlow = repository.fetchRemoteDogBreeds()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Success)
        }

    @Test
    fun `Given getAllDogBreeds is unsuccessful WHEN fetchRemoteDogBreeds is called THEN return Resource Error`() =
        runTest {
            // Arrange
            val mockResponse = mockk<Response<GetAllBreedsResponse>>(relaxed = true)
            every { mockResponse.isSuccessful } returns false
            every { mockResponse.body() } returns null
            coEvery { mockBreedApi.getAllDogBreeds() } returns mockResponse

            // Act
            val resultFlow = repository.fetchRemoteDogBreeds()
            val resultList = resultFlow.toList()

            // Assert
            assertTrue(resultList.first() is Resource.Loading)
            assertTrue(resultList.last() is Resource.Error)
        }

    @Test
    fun `Given insertDogBreeds is successful WHEN saveDogBreedsToCache is called THEN return true`() =
        runTest {
            // Arrange
            val dogBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "affenpinscher",
                    subBreeds = listOf("subBreed1", "SubBreed2"),
                ),
                DogBreed(
                    id = 2,
                    name = "african",
                    subBreeds = emptyList(),
                ),
            )

            val dogBreedEntities = dogBreeds.map { it.toDogBreedsEntity() }

            coEvery { dogBreedsDao.insertDogBreeds(dogBreedEntities) } returns listOf(1L, 2L)

            // Act
            val result = repository.saveDogBreedsToCache(dogBreeds)

            // Assert
            assertTrue(result)
        }

    @Test
    fun `Given insertDogBreeds is not successful WHEN saveDogBreedsToCache is called THEN return true`() =
        runTest {
            // Arrange
            val dogBreeds = listOf(
                DogBreed(
                    id = 1,
                    name = "affenpinscher",
                    subBreeds = listOf("subBreed1", "SubBreed2"),
                ),
                DogBreed(
                    id = 2,
                    name = "african",
                    subBreeds = emptyList(),
                ),
            )

            val dogBreedEntities = dogBreeds.map { it.toDogBreedsEntity() }

            coEvery { dogBreedsDao.insertDogBreeds(dogBreedEntities) } returns emptyList()

            // Act
            val result = repository.saveDogBreedsToCache(dogBreeds)

            // Assert
            assertFalse(result)
        }
}
