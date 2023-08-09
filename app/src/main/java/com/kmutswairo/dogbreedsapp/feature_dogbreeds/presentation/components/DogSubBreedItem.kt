package com.kmutswairo.dogbreedsapp.feature_dogbreeds.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DogSubBreedItem(
    modifier: Modifier = Modifier,
    subBreed: String,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Box(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 5.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = subBreed)
        }
    }
}

@Preview
@Composable
fun DogSubBreedItemPreview() {
    DogSubBreedItem(subBreed = "Husky")
}
