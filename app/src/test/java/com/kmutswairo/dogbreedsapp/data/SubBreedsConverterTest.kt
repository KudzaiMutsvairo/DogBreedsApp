package com.kmutswairo.dogbreedsapp.data

import org.junit.Assert.assertEquals
import org.junit.Test

class SubBreedsConverterTest {
    @Test
    fun `Given list of Strings WHEN fromList THEN return a comma-separated string of sub-breeds`() {
        // Arrange
        val subBreeds = listOf("Golden Retriever", "Labrador Retriever")

        // Act
        val converter = SubBreedsConverter()
        val result = converter.fromList(subBreeds)

        // Assert
        assertEquals(result, "Golden Retriever, Labrador Retriever")
    }

    @Test
    fun `Given list of String WHEN toList THEN return a list of sub-breeds from a comma-separated string`() {
        // Arrange
        val data = "Golden Retriever, Labrador Retriever"

        // Act
        val converter = SubBreedsConverter()
        val result = converter.toList(data)

        // Assert
        assertEquals(result, listOf("Golden Retriever", "Labrador Retriever"))
    }

    @Test
    fun `GIVEN an empty list HWN fromList THEN return an empty string`() {
        // Arrange
        val subBreeds = emptyList<String>()

        // Act
        val converter = SubBreedsConverter()
        val result = converter.fromList(subBreeds)

        // Assert
        assert(result.isEmpty())
    }

    @Test
    fun `GIVEN empty String WHEN toList THEN return an empty list`() {
        // Arrange
        val data = ""

        // Act
        val converter = SubBreedsConverter()
        val result = converter.toList(data)

        // Assert
        assert(result.isEmpty())
    }
}