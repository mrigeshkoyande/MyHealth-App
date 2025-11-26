package google.com.myhealth.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

enum class MealCategory {
    BREAKFAST,
    LUNCH,
    DINNER,
    SNACK
}

data class NutritionEntry(
    val name: String,
    val category: MealCategory,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fats: Float,
    val timestamp: Long = System.currentTimeMillis()
)

data class FoodDatabaseEntry(
    val name: String,
    val servingSize: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fats: Float
)

@Composable
fun NutritionScreen() {
    var showAddDialog by remember { mutableStateOf(false) }
    var showFoodSearch by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(MealCategory.BREAKFAST) }
    
    // Sample food database
    remember {
        listOf(
            FoodDatabaseEntry("Apple", "1 medium", 95, 0.5f, 25f, 0.3f),
            FoodDatabaseEntry("Chicken Breast", "100g", 165, 31f, 0f, 3.6f),
            FoodDatabaseEntry("Brown Rice", "100g", 111, 2.6f, 23f, 0.9f),
            // Add more common foods...
        )
    }

    var nutritionEntries by remember { 
        mutableStateOf(listOf(
            NutritionEntry("Oatmeal with Banana", MealCategory.BREAKFAST, 350, 12f, 65f, 6f),
            NutritionEntry("Grilled Chicken Salad", MealCategory.LUNCH, 450, 35f, 25f, 22f),
            NutritionEntry("Protein Bar", MealCategory.SNACK, 200, 15f, 25f, 8f)
        ))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Nutrition Tracker",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Category Filter
        CategorySelector(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Daily Summary Card
        NutritionSummaryCard(nutritionEntries)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Filtered Nutrition Entries
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(nutritionEntries.filter {
                selectedCategory == it.category
            }) { entry ->
                NutritionEntryCard(entry)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FloatingActionButton(
                onClick = { showFoodSearch = true },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Search, "Search Food Database")
            }
            
            FloatingActionButton(
                onClick = { showAddDialog = true },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, "Add Manual Entry")
            }
        }
    }

    if (showFoodSearch) {
        FoodSearchDialog()
    }

    if (showAddDialog) {
        AddNutritionEntryDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { entry ->
                nutritionEntries = nutritionEntries + entry
                showAddDialog = false
            }
        )
    }
}

@Composable
fun FoodSearchDialog() {

}

@Composable
private fun NutritionSummaryCard(entries: List<NutritionEntry>) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Daily Summary",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            
            val totalCalories = entries.sumOf { it.calories }
            val totalProtein = entries.sumOf { it.protein.toDouble() }
            val totalCarbs = entries.sumOf { it.carbs.toDouble() }
            val totalFats = entries.sumOf { it.fats.toDouble() }
            
            MacroProgressBar("Calories", totalCalories.toFloat(), 2000f)
            MacroProgressBar("Protein", totalProtein.toFloat(), 150f)
            MacroProgressBar("Carbs", totalCarbs.toFloat(), 250f)
            MacroProgressBar("Fats", totalFats.toFloat(), 65f)
        }
    }
}

@Composable
private fun MacroProgressBar(label: String, current: Float, target: Float) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${current.toInt()}/${target.toInt()}g",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        LinearProgressIndicator(
            progress = { (current / target).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun NutritionEntryCard(entry: NutritionEntry) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = entry.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NutrientInfo("Calories", entry.calories.toString())
                NutrientInfo("Protein", "${entry.protein}g")
                NutrientInfo("Carbs", "${entry.carbs}g")
                NutrientInfo("Fats", "${entry.fats}g")
            }
        }
    }
}

@Composable
private fun NutrientInfo(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AddNutritionEntryDialog(
    onDismiss: () -> Unit,
    onAdd: (NutritionEntry) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein by remember { mutableStateOf("") }
    var carbs by remember { mutableStateOf("") }
    var fats by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Nutrition Entry") },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Meal Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = calories,
                    onValueChange = { calories = it },
                    label = { Text("Calories") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = protein,
                    onValueChange = { protein = it },
                    label = { Text("Protein (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = carbs,
                    onValueChange = { carbs = it },
                    label = { Text("Carbs (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = fats,
                    onValueChange = { fats = it },
                    label = { Text("Fats (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && calories.isNotBlank() &&
                        protein.isNotBlank() && carbs.isNotBlank() && fats.isNotBlank()
                    ) {
                        onAdd(
                            NutritionEntry(
                                name = name,
                                category = MealCategory.BREAKFAST,
                                calories = calories.toIntOrNull() ?: 0,
                                protein = protein.toFloatOrNull() ?: 0f,
                                carbs = carbs.toFloatOrNull() ?: 0f,
                                fats = fats.toFloatOrNull() ?: 0f
                            )
                        )
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun CategorySelector(
    selectedCategory: MealCategory,
    onCategorySelected: (MealCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        MealCategory.entries.forEach { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text(category.name.lowercase()
                    .replaceFirstChar
                    { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) }
            )
        }
    }
} 