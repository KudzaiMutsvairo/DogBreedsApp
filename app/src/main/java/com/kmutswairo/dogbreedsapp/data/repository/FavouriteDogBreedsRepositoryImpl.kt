package com.kmutswairo.dogbreedsapp.data.repository

import com.kmutswairo.dogbreedsapp.data.local.favourites.FavDogBreedsDao
import com.kmutswairo.dogbreedsapp.data.mapper.toFavoriteDogBreedEntity
import com.kmutswairo.dogbreedsapp.data.mapper.toFavouriteDogBreed
import com.kmutswairo.dogbreedsapp.domain.model.FavouriteDogBreed
import com.kmutswairo.dogbreedsapp.domain.repository.FavouriteDogBreedsRepository
import com.kmutswairo.dogbreedsapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteDogBreedsRepositoryImpl @Inject constructor(
    private val favouriteDogBreedsDao: FavDogBreedsDao,
) : FavouriteDogBreedsRepository {

    override suspend fun insertFavouriteDogBreed(favDogBreed: FavouriteDogBreed): Resource<Long> {
        return try {
            val result = favouriteDogBreedsDao.insertFavDogBreed(favDogBreed.toFavoriteDogBreedEntity())
            Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error("An error occurred while fetching data")
        }
    }

    override suspend fun deleteFavouriteDogBreed(id: Int): Resource<Boolean> {
        return try {
            val rowsAffected = favouriteDogBreedsDao.deleteFavDogBreed(id)
            if (rowsAffected > 0) {
                Resource.Success(true)
            } else {
                Resource.Error("Error removing favourite dog breed")
            }
        } catch (e: Exception) {
            Resource.Error("Error removing favourite dog breed")
        }
    }

    override suspend fun getAllFavouriteDogBreeds(): Flow<Resource<List<FavouriteDogBreed>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val favDogBreeds = favouriteDogBreedsDao.getAllFavDogBreeds()
                emit(
                    Resource.Success(
                        data = favDogBreeds.map { it.toFavouriteDogBreed() },
                    ),
                )
            } catch (e: Exception) {
                emit(Resource.Error("An error occurred while fetching data"))
            }
        }
    }
}
