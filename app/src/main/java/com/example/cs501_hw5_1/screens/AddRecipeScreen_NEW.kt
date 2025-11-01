package com.example.cs501_hw5_1.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cs501_hw5_1.navigation.Routes

@Composable
fun AddRecipeScreen(
    onAddRecipe: (String, List<String>, List<String>) -> Unit,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf("")) }
    var steps by remember { mutableStateOf(listOf("")) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Add new recipe",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                showError = false
            },
            label = { Text("name") },
            placeholder = { Text("ex: Kung Pao Chicken") },
            modifier = Modifier.fillMaxWidth(),
            isError = showError && title.isBlank(),
            supportingText = {
                if (showError && title.isBlank()) {
                    Text("Please enter the recipe name")
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ingredients list",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ingredients.forEachIndexed { index, ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = ingredient,
                    onValueChange = { newValue ->
                        ingredients = ingredients.toMutableList().apply {
                            this[index] = newValue
                        }
                        showError = false
                    },
                    label = { Text("Ingredients ${index + 1}") },
                    placeholder = { Text("ex：Chicken breast 300g") },
                    modifier = Modifier.weight(1f),
                    isError = showError && ingredient.isBlank()
                )
                if (ingredients.size > 1) {
                    IconButton(onClick = {
                        ingredients = ingredients.toMutableList().apply { removeAt(index) }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete ingredient",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }

        OutlinedButton(
            onClick = { ingredients = ingredients + "" },
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Ingredient")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Steps",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = step,
                    onValueChange = { newValue ->
                        steps = steps.toMutableList().apply {
                            this[index] = newValue
                        }
                        showError = false
                    },
                    label = { Text("Step ${index + 1}") },
                    placeholder = { Text("Please describe it") },
                    modifier = Modifier.weight(1f),
                    minLines = 2,
                    isError = showError && step.isBlank()
                )
                if (steps.size > 1) {
                    IconButton(onClick = {
                        steps = steps.toMutableList().apply { removeAt(index) }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete step",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        }

        OutlinedButton(
            onClick = { steps = steps + "" },
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Add Step")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (title.isNotBlank() &&
                    ingredients.any { it.isNotBlank() } &&
                    steps.any { it.isNotBlank() }
                ) {
                    onAddRecipe(
                        title,
                        ingredients.filter { it.isNotBlank() },
                        steps.filter { it.isNotBlank() }
                    )
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                } else {
                    showError = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = title.isNotBlank() &&
                    ingredients.any { it.isNotBlank() } &&
                    steps.any { it.isNotBlank() }
        ) {
            Text("Save Recipe")
        }

        if (showError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please fill in all required fields.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}



