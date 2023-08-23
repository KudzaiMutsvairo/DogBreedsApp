package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.model.DogBreed

@Composable
fun DogBreedItem(
    modifier: Modifier = Modifier,
    breed: DogBreed,
    onFavoriteToggle: (DogBreed) -> Unit,
) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(modifier = Modifier.padding(3.dp)) {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = breed.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                FavouriteToggleIconButton(
                    breed = breed,
                    onFavoriteToggle = onFavoriteToggle,
                )
            }
            if (breed.subBreeds.isNotEmpty()) {
                LazyRow {
                    items(breed.subBreeds) { item ->
                        DogSubBreedItem(subBreed = item)
                    }
                }
            }
        }
    }
}

@Composable
fun FavouriteToggleIconButton(
    breed: DogBreed,
    onFavoriteToggle: (DogBreed) -> Unit,
) {
    IconButton(
        modifier = Modifier,
        onClick = { onFavoriteToggle(breed) },
    ) {
        Icon(
            imageVector = if (breed.isFavourite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Add to Favorites",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DogBreedItemPreview() {
    DogBreedItem(
        breed = DogBreed(
            name = "Poodle",
            isFavourite = false,
            subBreeds = listOf(
                "Toy Poodle",
                "Miniature Poodle",
                "Standard Poodle",
            ),
        ),
        onFavoriteToggle = {},
    )
}
