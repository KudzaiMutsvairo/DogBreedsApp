package com.kmutswairo.dogbreedsapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ScreenTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenTitlePreview() {
    ScreenTitle(title = "Title")
}
