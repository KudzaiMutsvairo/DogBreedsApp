package com.kmutswairo.dogbreedsapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kmutswairo.dogbreedsapp.R

@Composable
fun NoDataComponent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(250.dp),
                painter = painterResource(id = R.drawable.svg_empty),
                contentDescription = "Empty",
            )
        }
        Text(
            text = "No data",
            style = MaterialTheme.typography.h3,
        )
    }
}

@Preview
@Composable
fun NoDataComponentPreview() {
    NoDataComponent()
}
