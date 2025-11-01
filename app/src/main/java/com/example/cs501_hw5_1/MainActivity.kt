package com.example.cs501_hw5_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cs501_hw5_1.navigation.Routes
import com.example.cs501_hw5_1.screens.*
import com.example.cs501_hw5_1.ui.theme.CS501_HW5_1Theme
import com.example.cs501_hw5_1.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CS501_HW5_1Theme {
                RecipeApp()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    
    val viewModel: RecipeViewModel = viewModel()
    
    val bottomNavItems = listOf(
        BottomNavItem("Home", Routes.Home.route, Icons.Default.Home),
        BottomNavItem("Add", Routes.AddRecipe.route, Icons.Default.Add),
        BottomNavItem("Setting", Routes.Settings.route, Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == item.route
                        } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(
                    recipes = viewModel.recipes,
                    navController = navController
                )
            }

            composable(Routes.AddRecipe.route) {
                AddRecipeScreen(
                    onAddRecipe = { title, ingredients, steps ->
                        viewModel.addRecipe(title, ingredients, steps)
                    },
                    navController = navController
                )
            }

            composable(Routes.Settings.route) {
                SettingsScreen()
            }

            composable(
                route = Routes.RecipeDetail.route,
                arguments = listOf(
                    navArgument("recipeId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId")
                val recipe = recipeId?.let { viewModel.getRecipeById(it) }

                RecipeDetailScreen(
                    recipe = recipe,
                    navController = navController
                )
            }
        }
    }
}
data class BottomNavItem(
    val title: String,
    val route: String,
    val icon: ImageVector
)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecipeAppPreview() {
    CS501_HW5_1Theme {
        RecipeApp()
    }
}

