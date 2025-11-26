package google.com.myhealth.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Dashboard : BottomNavItem(Screen.Dashboard.route, Icons.Default.Home, "Dashboard")
    data object Exercise : BottomNavItem(Screen.Exercise.route, Icons.Default.DirectionsRun, "Exercise")
    data object Nutrition : BottomNavItem(Screen.Nutrition.route, Icons.Default.Restaurant, "Nutrition")
}

@Composable
fun BottomNavBar(navController: NavController) {
    NavigationBar {
        val items = listOf(
            BottomNavItem.Dashboard,
            BottomNavItem.Exercise,
            BottomNavItem.Nutrition
        )
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
} 