@file:Suppress("UNUSED_EXPRESSION")

package google.com.myhealth.screens

import android.annotation.SuppressLint
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.data.UiToolingDataApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlin.TODO as TODO1
import kotlin.Unit as Unit1

@Composable
fun DashboardScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val showScrollToTop by remember {
        derivedStateOf { scrollState.value > 100 }
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState)
                .drawVerticalScrollbar(
                    state = scrollState,
                    isScrollInProgress = scrollState.isScrollInProgress
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Health Dashboard",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))
            
            // Weekly AOI Index Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Weekly Activity Overview",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Pie Chart
                    AOIPieChart(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Activity breakdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ActivityLegendItem("High", MaterialTheme.colorScheme.primary)
                        ActivityLegendItem("Moderate", MaterialTheme.colorScheme.secondary)
                        ActivityLegendItem("Low", MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Add Weekly Progress Card
            WeeklyProgressCard()
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quick stats cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard("Steps", "5,430")
                StatCard("Calories", "1,200")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Navigation buttons
            Button(onClick = { navController.navigate("exercise") }) {
                Text("Exercises")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(onClick = { navController.navigate("nutrition") }) {
                Text("Nutrition")
            }
        }
        
        // Scroll to top button
        AnimatedVisibility(
            visible = showScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically()
        ) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        scrollState.animateScrollTo(0, animationSpec = tween(800))
                    }
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "Scroll to top"
                )
            }
        }
    }
}

@OptIn(UiToolingDataApi::class)
@Composable
private fun AOIPieChart(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            PieChart(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Basic default configuration (tweak as needed)
                description.isEnabled = false
                setUsePercentValues(false)
                setDrawEntryLabels(false)
                setDrawHoleEnabled(true)
            }
        },
        modifier = modifier
    )
}

fun setValueFormatter(): Unit1 = Unit1

fun animateXY() = Unit1

@Composable
fun Invalidate() {


    Row(
        content = {
            TODO1(reason = "Not yet implemented")
        },
    )
    
}

fun setValueTextSize() {

}

fun setDrawValues() {

}

fun Float.setCenterTextSize() {

}

class PieDataSet {
    class ValuePosition

}

fun setValueTextColor() {

}

fun setDrawEntryLabels() {

}

fun setTransparentCircleRadius() {

}

fun setHoleColor() {

}

class PieEntry

@Composable
private fun ActivityLegendItem(label: String, color: Color) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(4.dp)
) {
    Surface(
        modifier = Modifier.size(12.dp),
        color = color,
        shape = MaterialTheme.shapes.small
    ) {}
    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
private fun StatCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title)
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
private fun WeeklyProgressCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Weekly Activity Progress",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            // Add total weekly summary
            Text(
                text = "Total Active Minutes: 840",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Sample weekly data with improved progress bars
            WeeklyProgressBar("Mon", 0.7f, "105 min")
            WeeklyProgressBar("Tue", 0.8f, "120 min")
            WeeklyProgressBar("Wed", 0.6f, "90 min")
            WeeklyProgressBar("Thu", 0.9f, "135 min")
            WeeklyProgressBar("Fri", 0.5f, "75 min")
            WeeklyProgressBar("Sat", 0.4f, "60 min")
            WeeklyProgressBar("Sun", 0.3f, "45 min")
        }
    }
}

@Composable
private fun WeeklyProgressBar(day: String, progress: Float, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = day,
            modifier = Modifier.width(40.dp),
            style = MaterialTheme.typography.bodySmall
        )
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .weight(1f)
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Text(
            text = duration,
            modifier = Modifier.width(60.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.End
        )
    }
}

/**
 * Adds a vertical scrollbar to the composable it is applied to, visualizing the scroll state.
 *
 * This modifier draws a vertical scrollbar on the right edge of the composable, indicating the current
 * scroll position and the total scrollable range. The scrollbar's height is proportional to the
 * visible content height relative to the total content height.
 *
 * @param state The [ScrollState] that this scrollbar is linked to. It provides information
 *              about the current scroll position and the maximum scroll range.
 * @param isScrollInProgress A boolean indicating whether a scroll gesture is currently in
 *                           progress. This affects the opacity of the scrollbar.
 * @return A [Modifier] that draws the vertical scrollbar.
 *
 * The scrollbar is only drawn if:
 *   - The maximum scroll value (state.maxValue) is greater than 0, meaning there is content
 *     beyond the initially visible area.
 *   - The calculated scrollbar height is greater than 0, ensuring it is visible.
 *   - The calculated scrollbar Y position is non-negative.
 *
 * The scrollbar's appearance is as follows:
 *   - **Position:** On the right edge of the composable.
 *   - **Width:** 4.dp.
 *   - **Height:** Proportional to the ratio of visible content height to the total content height,
 *     but at least 40 pixels to ensure visibility.
 *   - **Y Position:** Calculated based on the current scroll position relative to the total
 *     scrollable range.
 *   - **Color:** The primary color from the MaterialTheme, with opacity dependent on
 *     `isScrollInProgress`.
 *   - **Opacity:** 0.8f when a scroll is in progress, 0.5f otherwise.
 *
 * Usage Example:
 * ```
 * Box(modifier = Modifier.fillMaxSize */// Enhanced scrollbar with animation
@SuppressLint("UnnecessaryComposedModifier")
@Composable
private fun Modifier.drawVerticalScrollbar(
    state: ScrollState,
    isScrollInProgress: Boolean
) = drawWithContent {
    drawContent()

    val height: Float = size.height
    val maxScroll = state.maxValue.toFloat()
    val currentScroll = state.value.toFloat()
    val scrollbarHeight = (height * (height / (height + maxScroll))).coerceAtLeast(40f)
    if (maxScroll > 0) (height - scrollbarHeight) * currentScroll / maxScroll else 0f
    if (isScrollInProgress) 0.8f else 0.5f

}

