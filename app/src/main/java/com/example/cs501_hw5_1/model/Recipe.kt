package com.example.cs501_hw5_1.model


data class Recipe(
    val id: String,
    val title: String,
    val ingredients: List<String>,
    val steps: List<String>
)

