package com.example.cs501_hw5_1.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cs501_hw5_1.model.Recipe
import java.util.UUID

class RecipeViewModel : ViewModel() {
    private val _recipes = mutableStateListOf<Recipe>()
    val recipes: List<Recipe> get() = _recipes

    init {
        _recipes.addAll(
            listOf(
                Recipe(
                    id = UUID.randomUUID().toString(),
                    title = "Tomato and Egg Stir-fry",
                    ingredients = listOf(
                        "2 tomatoes",
                        "3 eggs",
                        "Salt — to taste",
                        "1 spoon of sugar",
                        "Chopped scallions — to taste"
                    ),
                    steps = listOf(
                        "Cut the tomatoes into small pieces",
                        "Beat the eggs and add a pinch of salt",
                        "Heat oil and scramble the eggs until cooked, then remove from the pan",
                        "Reheat oil and stir-fry the tomatoes until soft",
                        "Add the cooked eggs back in, season with salt and sugar",
                        "Sprinkle chopped scallions and serve"
                    )
                ),
                Recipe(
                    id = UUID.randomUUID().toString(),
                    title = "Kung Pao Chicken",
                    ingredients = listOf(
                        "300g chicken breast",
                        "50g peanuts",
                        "Dried chili peppers — to taste",
                        "Sichuan peppercorns — to taste",
                        "Chopped scallions, ginger, and garlic — to taste",
                        "Soy sauce, vinegar, and sugar — to taste"
                    ),
                    steps = listOf(
                        "Cut the chicken breast into cubes and marinate with cooking wine and starch for 15 minutes",
                        "Prepare the Kung Pao sauce by mixing soy sauce, vinegar, sugar, and starch",
                        "Fry the peanuts in hot oil until golden brown",
                        "Stir-fry dried chili peppers and Sichuan peppercorns until fragrant",
                        "Add the chicken cubes and stir-fry until they change color",
                        "Pour in the sauce, add the peanuts, and stir-fry quickly before serving"
                    )
                ),
                Recipe(
                    id = UUID.randomUUID().toString(),
                    title = "Braised Pork Belly",
                    ingredients = listOf(
                        "500g pork belly",
                        "30g rock sugar",
                        "3 slices of ginger",
                        "2 star anise",
                        "1 small piece of cinnamon stick",
                        "Cooking wine and soy sauce — to taste"
                    ),
                    steps = listOf(
                        "Cut the pork belly into chunks and blanch it in cold water",
                        "Add a little oil to the pan, then melt the rock sugar to make caramel color",
                        "Add the pork belly and stir-fry until it’s evenly coated with color",
                        "Add ginger slices, star anise, and cinnamon stick",
                        "Pour in cooking wine, soy sauce, and hot water",
                        "Bring to a boil, then simmer over low heat for 40 minutes",
                        "Increase to high heat and reduce the sauce until thick"
                    )
                )
            )
        )
    }

    fun addRecipe(title: String, ingredients: List<String>, steps: List<String>) {
        val newRecipe = Recipe(
            id = UUID.randomUUID().toString(),
            title = title,
            ingredients = ingredients,
            steps = steps
        )
        _recipes.add(newRecipe)
    }

    fun getRecipeById(id: String): Recipe? {
        return _recipes.find { it.id == id }
    }
}

