package google.com.myhealth.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import google.com.myhealth.ui.theme.Teal40

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    data object Dashboard : BottomNavItem(Screen.Dashboard.route, Icons.Default.Home, "Dashboard")
    data object Exercise  : BottomNavItem(Screen.Exercise.route, Icons.AutoMirrored.Filled.DirectionsRun, "Exercise")
    data object Nutrition : BottomNavItem(Screen.Nutrition.route, Icons.Default.Restaurant, "Nutrition")
}

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Exercise,
        BottomNavItem.Nutrition
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // Floating pill container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 16.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    BottomNavItemView(
                        item     = item,
                        selected = selected,
                        onClick  = {
                            if (!selected) {
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
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue   = if (selected) Teal40.copy(alpha = 0.12f) else Color.Transparent,
        animationSpec = tween(300),
        label         = "nav_bg"
    )
    val contentColor by animateColorAsState(
        targetValue   = if (selected) Teal40 else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(300),
        label         = "nav_color"
    )

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = if (selected) 20.dp else 16.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector        = item.icon,
                contentDescription = item.label,
                tint               = contentColor
            )
            if (selected) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text  = item.label,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color      = contentColor,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}