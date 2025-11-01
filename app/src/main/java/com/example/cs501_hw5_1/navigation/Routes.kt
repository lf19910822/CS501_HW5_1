package com.example.cs501_hw5_1.navigation
sealed class Routes(val route: String) {

    object Home : Routes("home")

    object AddRecipe : Routes("add_recipe")

    object Settings : Routes("settings")

    object RecipeDetail : Routes("recipe_detail/{recipeId}") {
        fun createRoute(recipeId: String) = "recipe_detail/$recipeId"
    }
}

