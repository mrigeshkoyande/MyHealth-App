package google.com.myhealth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import google.com.myhealth.screens.DashboardScreen
import google.com.myhealth.screens.ExerciseScreen
import google.com.myhealth.screens.NutritionScreen

sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object Exercise : Screen("exercise")
    data object Nutrition : Screen("nutrition")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }
        composable(Screen.Exercise.route) {
            ExerciseScreen(navController)
        }
        composable(Screen.Nutrition.route) {
            NutritionScreen()
        }
    }
} 