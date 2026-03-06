package google.com.myhealth.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.BrunchDining
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import google.com.myhealth.ui.components.AnimatedProgressRing
import google.com.myhealth.ui.components.GlassCard
import google.com.myhealth.ui.components.MacroCard
import google.com.myhealth.ui.components.SectionHeader
import google.com.myhealth.ui.theme.CalorieRed
import google.com.myhealth.ui.theme.CarbOrange
import google.com.myhealth.ui.theme.Emerald40
import google.com.myhealth.ui.theme.FatGreen
import google.com.myhealth.ui.theme.ProteinBlue
import google.com.myhealth.ui.theme.Teal40
import java.util.Locale

// ── Data models (preserved from original) ────────────────────────────────────

enum class MealCategory { BREAKFAST, LUNCH, DINNER, SNACK }

data class NutritionEntry(
    val name:      String,
    val category:  MealCategory,
    val calories:  Int,
    val protein:   Float,
    val carbs:     Float,
    val fats:      Float,
    val timestamp: Long = System.currentTimeMillis()
)

data class FoodDatabaseEntry(
    val name:        String,
    val servingSize: String,
    val calories:    Int,
    val protein:     Float,
    val carbs:       Float,
    val fats:        Float
)

// ─────────────────────────── NutritionScreen ─────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionScreen() {
    var showAddSheet by remember { mutableStateOf(false) }
    var addCategory  by remember { mutableStateOf(MealCategory.BREAKFAST) }

    var nutritionEntries by remember {
        mutableStateOf(
            listOf(
                NutritionEntry("Oatmeal with Banana",    MealCategory.BREAKFAST, 350, 12f, 65f, 6f),
                NutritionEntry("Greek Yogurt",           MealCategory.BREAKFAST, 150, 15f, 12f, 3f),
                NutritionEntry("Grilled Chicken Salad",  MealCategory.LUNCH,     450, 35f, 25f, 22f),
                NutritionEntry("Brown Rice",             MealCategory.LUNCH,     220,  5f, 48f,  2f),
                NutritionEntry("Salmon & Veg",           MealCategory.DINNER,    520, 42f, 18f, 28f),
                NutritionEntry("Protein Bar",            MealCategory.SNACK,     200, 15f, 25f,  8f)
            )
        )
    }

    val totalCalories = nutritionEntries.sumOf { it.calories }
    val totalProtein  = nutritionEntries.sumOf { it.protein.toDouble() }.toFloat()
    val totalCarbs    = nutritionEntries.sumOf { it.carbs.toDouble() }.toFloat()
    val totalFats     = nutritionEntries.sumOf { it.fats.toDouble() }.toFloat()
    val goalCalories  = 2000f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier        = Modifier.fillMaxSize(),
            contentPadding  = PaddingValues(bottom = 100.dp)
        ) {
            // ── Header ──────────────────────────────────────────────────────
            item {
                NutritionHeader()
            }

            // ── Calorie Hero Ring ────────────────────────────────────────────
            item {
                Spacer(Modifier.height(16.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    GlassCard(modifier = Modifier.fillMaxWidth()) {
                        CalorieHeroSection(
                            consumed = totalCalories.toFloat(),
                            goal     = goalCalories,
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            }

            // ── Macro Row ────────────────────────────────────────────────────
            item {
                Spacer(Modifier.height(20.dp))
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SectionHeader(title = "Today's Macros")
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MacroCard(
                            label   = "Protein",
                            current = totalProtein,
                            target  = 150f,
                            unit    = "g",
                            color   = ProteinBlue,
                            modifier = Modifier.weight(1f)
                        )
                        MacroCard(
                            label   = "Carbs",
                            current = totalCarbs,
                            target  = 250f,
                            unit    = "g",
                            color   = CarbOrange,
                            modifier = Modifier.weight(1f)
                        )
                        MacroCard(
                            label   = "Fat",
                            current = totalFats,
                            target  = 65f,
                            unit    = "g",
                            color   = FatGreen,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // ── Meal Sections ─────────────────────────────────────────────────
            MealCategory.values().forEach { cat ->
                val catEntries = nutritionEntries.filter { it.category == cat }
                val catCals   = catEntries.sumOf { it.calories }
                if (catEntries.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(20.dp))
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            MealSectionHeader(
                                category = cat,
                                totalCalories = catCals,
                                onAdd = { addCategory = cat; showAddSheet = true }
                            )
                        }
                    }
                    items(catEntries) { entry ->
                        NutritionEntryCard(
                            entry    = entry,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    item {
                        Spacer(Modifier.height(20.dp))
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            MealSectionHeader(
                                category      = cat,
                                totalCalories = 0,
                                onAdd         = { addCategory = cat; showAddSheet = true }
                            )
                            Spacer(Modifier.height(4.dp))
                            EmptyMealSlot(category = cat)
                        }
                    }
                }
            }
        }

        // ── FAB ───────────────────────────────────────────────────────────────
        FloatingActionButton(
            onClick        = { showAddSheet = true },
            modifier       = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = CalorieRed,
            contentColor   = Color.White
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Meal")
        }
    }

    // ── Add Meal Bottom Sheet ──────────────────────────────────────────────────
    if (showAddSheet) {
        AddMealBottomSheet(
            defaultCategory = addCategory,
            onDismiss       = { showAddSheet = false },
            onAdd           = { entry ->
                nutritionEntries = nutritionEntries + entry
                showAddSheet = false
            }
        )
    }
}

// ─────────────────────────── Header ──────────────────────────────────────────

@Composable
private fun NutritionHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF3D1010), Color(0xFFEF5350), MaterialTheme.colorScheme.background)
                )
            )
            .padding(start = 20.dp, end = 20.dp, top = 52.dp, bottom = 24.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Restaurant,
                        contentDescription = null,
                        tint     = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "Nutrition",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White, fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        "Today's fuel tracker",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    )
                }
            }
        }
    }
}

// ─────────────────────────── Calorie Hero ────────────────────────────────────

@Composable
private fun CalorieHeroSection(
    consumed: Float,
    goal: Float,
    modifier: Modifier = Modifier
) {
    val progress = (consumed / goal).coerceIn(0f, 1f)
    val remaining = (goal - consumed).coerceAtLeast(0f)

    Column(
        modifier            = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Daily Calories",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(Modifier.height(16.dp))
        AnimatedProgressRing(
            progress      = progress,
            size          = 160.dp,
            strokeWidth   = 14.dp,
            trackColor    = MaterialTheme.colorScheme.surfaceVariant,
            progressColor = CalorieRed,
            centerContent = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "${consumed.toInt()}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color      = CalorieRed,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        "kcal",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            CalorieStat("Consumed",  "${consumed.toInt()} kcal",  CalorieRed)
            CalorieStat("Remaining", "${remaining.toInt()} kcal", Emerald40)
            CalorieStat("Goal",      "${goal.toInt()} kcal",      MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun CalorieStat(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            style = MaterialTheme.typography.labelLarge.copy(
                color = color, fontWeight = FontWeight.Bold
            )
        )
        Text(
            label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

// ─────────────────────────── Meal Section Header ─────────────────────────────

private fun mealIcon(cat: MealCategory): ImageVector = when (cat) {
    MealCategory.BREAKFAST -> Icons.Default.LocalCafe
    MealCategory.LUNCH     -> Icons.Default.BrunchDining
    MealCategory.DINNER    -> Icons.Default.DinnerDining
    MealCategory.SNACK     -> Icons.Default.Bedtime
}

private fun mealColor(cat: MealCategory): Color = when (cat) {
    MealCategory.BREAKFAST -> Color(0xFFFFA726) // orange
    MealCategory.LUNCH     -> Color(0xFF26A69A) // teal
    MealCategory.DINNER    -> Color(0xFF7E57C2) // purple
    MealCategory.SNACK     -> Color(0xFFEC407A) // pink
}

@Composable
private fun MealSectionHeader(
    category: MealCategory,
    totalCalories: Int,
    onAdd: () -> Unit
) {
    val color = mealColor(category)
    Row(
        modifier              = Modifier.fillMaxWidth(),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(mealIcon(category), contentDescription = null, tint = color, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(
                    category.name.lowercase().replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                    },
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                if (totalCalories > 0) {
                    Text(
                        "$totalCalories kcal",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
        TextButton(onClick = onAdd) {
            Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(16.dp), tint = color)
            Spacer(Modifier.width(4.dp))
            Text("Add", style = MaterialTheme.typography.labelMedium, color = color)
        }
    }
}

// ─────────────────────────── Nutrition Entry Card ────────────────────────────

@Composable
private fun NutritionEntryCard(entry: NutritionEntry, modifier: Modifier = Modifier) {
    val accentColor = mealColor(entry.category)

    Card(
        modifier  = modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier          = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Color accent bar
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(accentColor)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    entry.name,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    NutrientPill("P ${entry.protein.toInt()}g", ProteinBlue)
                    NutrientPill("C ${entry.carbs.toInt()}g",   CarbOrange)
                    NutrientPill("F ${entry.fats.toInt()}g",    FatGreen)
                }
            }
            // Calorie badge
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = accentColor.copy(alpha = 0.12f)
            ) {
                Text(
                    "${entry.calories}\nkcal",
                    style     = MaterialTheme.typography.labelMedium.copy(
                        color     = accentColor,
                        fontWeight = FontWeight.Bold
                    ),
                    textAlign = TextAlign.Center,
                    modifier  = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun NutrientPill(text: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(50.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Text(
            text,
            style    = MaterialTheme.typography.labelSmall.copy(color = color, fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
        )
    }
}

// ─────────────────────────── Empty Meal Slot ─────────────────────────────────

@Composable
private fun EmptyMealSlot(category: MealCategory) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(16.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier         = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp, horizontal = 16.dp)
        ) {
            Text(
                "No ${category.name.lowercase()} items — tap Add above",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

// ─────────────────────────── Add Meal Bottom Sheet ───────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddMealBottomSheet(
    defaultCategory: MealCategory,
    onDismiss: () -> Unit,
    onAdd: (NutritionEntry) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var name     by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein  by remember { mutableStateOf("") }
    var carbs    by remember { mutableStateOf("") }
    var fats     by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(defaultCategory) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState       = sheetState,
        shape            = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor   = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 4.dp, bottom = 32.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                "Add Meal",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Category chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MealCategory.values().forEach { cat ->
                    FilterChip(
                        selected = cat == category,
                        onClick  = { category = cat },
                        label    = {
                            Text(
                                cat.name.lowercase()
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            }

            OutlinedTextField(
                value         = name,
                onValueChange = { name = it },
                label         = { Text("Meal Name") },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                singleLine    = true
            )

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value         = calories,
                    onValueChange = { calories = it },
                    label         = { Text("Kcal") },
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(14.dp),
                    singleLine    = true
                )
                OutlinedTextField(
                    value         = protein,
                    onValueChange = { protein = it },
                    label         = { Text("Protein g") },
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(14.dp),
                    singleLine    = true
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value         = carbs,
                    onValueChange = { carbs = it },
                    label         = { Text("Carbs g") },
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(14.dp),
                    singleLine    = true
                )
                OutlinedTextField(
                    value         = fats,
                    onValueChange = { fats = it },
                    label         = { Text("Fat g") },
                    modifier      = Modifier.weight(1f),
                    shape         = RoundedCornerShape(14.dp),
                    singleLine    = true
                )
            }

            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        onAdd(
                            NutritionEntry(
                                name      = name,
                                category  = category,
                                calories  = calories.toIntOrNull()   ?: 0,
                                protein   = protein.toFloatOrNull()  ?: 0f,
                                carbs     = carbs.toFloatOrNull()    ?: 0f,
                                fats      = fats.toFloatOrNull()     ?: 0f
                            )
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape    = RoundedCornerShape(14.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = CalorieRed)
            ) {
                Text(
                    "Add Meal",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color      = Color.White
                    )
                )
            }
        }
    }
}