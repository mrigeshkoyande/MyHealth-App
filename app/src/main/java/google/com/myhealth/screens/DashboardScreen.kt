package google.com.myhealth.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import google.com.myhealth.ui.components.AnimatedProgressRing
import google.com.myhealth.ui.components.GlassCard
import google.com.myhealth.ui.components.MotivationalBanner
import google.com.myhealth.ui.components.SectionHeader
import google.com.myhealth.ui.components.StatCard
import google.com.myhealth.ui.theme.CalorieRed
import google.com.myhealth.ui.theme.CarbOrange
import google.com.myhealth.ui.theme.Emerald40
import google.com.myhealth.ui.theme.GradientTealEnd
import google.com.myhealth.ui.theme.GradientTealStart
import google.com.myhealth.ui.theme.Teal40
import google.com.myhealth.ui.theme.WaterBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardScreen(navController: NavController) {
    val scrollState      = rememberScrollState()
    val scope            = rememberCoroutineScope()
    val showScrollToTop  by remember { derivedStateOf { scrollState.value > 300 } }

    // stagger animation trigger
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(100); visible = true }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(scrollState)
                .padding(bottom = 24.dp)
        ) {
            // ── Header ────────────────────────────────────────────────────────
            DashboardHeader()

            Spacer(Modifier.height(16.dp))

            // ── Hero Activity Card ────────────────────────────────────────────
            val heroVisible by animateFloatAsState(
                if (visible) 1f else 0f,
                tween(600), label = "hero"
            )
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                HeroActivityCard()
            }

            Spacer(Modifier.height(20.dp))

            // ── Quick Stats Row ───────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title         = "Steps",
                    value         = "8,432",
                    unit          = "steps",
                    icon          = Icons.AutoMirrored.Filled.DirectionsRun,
                    gradientColors = listOf(Color(0xFF00BCD4), Color(0xFF006978)),
                    modifier      = Modifier.weight(1f)
                )
                StatCard(
                    title         = "Calories",
                    value         = "1,248",
                    unit          = "kcal",
                    icon          = Icons.Default.LocalFireDepartment,
                    gradientColors = listOf(Color(0xFFFF6B6B), Color(0xFFEE3D2D)),
                    modifier      = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title         = "Active Min",
                    value         = "47",
                    unit          = "min",
                    icon          = Icons.Default.Timer,
                    gradientColors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)),
                    modifier      = Modifier.weight(1f)
                )
                StatCard(
                    title         = "Water",
                    value         = "1.8",
                    unit          = "L",
                    icon          = Icons.Default.WaterDrop,
                    gradientColors = listOf(Color(0xFF29B6F6), Color(0xFF0277BD)),
                    modifier      = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            // ── Weekly Progress ───────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(title = "Weekly Activity")
                Spacer(Modifier.height(12.dp))
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    WeeklyBarChart(modifier = Modifier.padding(16.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Activity Distribution ─────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(title = "Activity Distribution")
                Spacer(Modifier.height(12.dp))
                GlassCard(modifier = Modifier.fillMaxWidth()) {
                    ActivityDistributionSection(modifier = Modifier.padding(16.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Motivational Banner ───────────────────────────────────────────
            MotivationalBanner(
                modifier       = Modifier.padding(horizontal = 16.dp),
                gradientColors = listOf(GradientTealStart, GradientTealEnd)
            )

            Spacer(Modifier.height(80.dp)) // bottom nav clearance
        }

        // ── Scroll-to-top FAB ────────────────────────────────────────────────
        AnimatedVisibility(
            visible  = showScrollToTop,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            enter = fadeIn() + slideInVertically { it },
            exit  = fadeOut() + slideOutVertically { it }
        ) {
            FloatingActionButton(
                onClick = { scope.launch { scrollState.animateScrollTo(0, tween(800)) } },
                containerColor = Teal40,
                elevation      = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "Scroll to top",
                    tint = Color.White
                )
            }
        }
    }
}

// ─────────────────────────── Header ──────────────────────────────────────────

@Composable
private fun DashboardHeader() {
    val today = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM")
    val hour = today.let { java.time.LocalTime.now().hour }
    val greeting = when {
        hour < 12 -> "Good Morning"
        hour < 17 -> "Good Afternoon"
        else      -> "Good Evening"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF006978), Color(0xFF00ACC1), MaterialTheme.colorScheme.background)
                )
            )
            .padding(start = 20.dp, end = 20.dp, top = 52.dp, bottom = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text  = "$greeting 👋",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.85f)
                    )
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text  = "Mrigesh",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color      = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text  = today.format(formatter),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
            }
            Box(
                modifier        = Modifier
                    .size(52.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

// ─────────────────────────── Hero Activity Card ───────────────────────────────

@Composable
private fun HeroActivityCard() {
    val activeMinutes  = 47f
    val goalMinutes    = 60f
    val progress       = activeMinutes / goalMinutes

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 16.dp, shape = RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF00BCD4), Color(0xFF004D56))
                )
            )
            .padding(24.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.5f)) {
                Text(
                    "Today's Activity",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    "${activeMinutes.toInt()}",
                    style = MaterialTheme.typography.displaySmall.copy(
                        color      = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    "/ ${goalMinutes.toInt()} min goal",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.7f)
                    )
                )
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint              = Color(0xFFFF6B6B),
                        modifier          = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Heart rate: 74 bpm",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    )
                }
            }
            AnimatedProgressRing(
                progress      = progress,
                size          = 96.dp,
                strokeWidth   = 10.dp,
                trackColor    = Color.White.copy(alpha = 0.2f),
                progressColor = Color.White,
                centerContent = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "${(progress * 100).toInt()}%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color      = Color.White,
                                fontWeight = FontWeight.ExtraBold
                            )
                        )
                        Text(
                            "Done",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        )
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// ─────────────────────────── Weekly Bar Chart ─────────────────────────────────

private val weekData = listOf(
    "M" to 0.70f, "T" to 0.85f, "W" to 0.60f, "T" to 0.92f,
    "F" to 0.55f, "S" to 0.45f, "S" to 0.30f
)

@Composable
private fun WeeklyBarChart(modifier: Modifier = Modifier) {
    var drawn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(200); drawn = true }

    // Create animated targets state
    val targets = weekData.map { (_, target) -> if (drawn) target else 0f }
    val anims = targets.mapIndexed { index, targetValue ->
        animateFloatAsState(
            targetValue    = targetValue,
            animationSpec  = tween(900, easing = FastOutSlowInEasing),
            label          = "bar_$index"
        )
    }

    val barColor   = Teal40
    val dimColor   = CarbOrange
    val todayIndex = 3 // Thursday = index 3 as most progress

    Column(modifier = modifier) {
        Text(
            "Total Active: 630 min this week",
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        Spacer(Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            val barWidth  = size.width / weekData.size
            val spacing   = 16.dp.toPx()
            val maxHeight = size.height - 24.dp.toPx()

            weekData.forEachIndexed { i, (label, _) ->
                val anim   = anims[i].value
                val x      = i * barWidth + (spacing / 2)
                val bh     = anim * maxHeight
                val y      = maxHeight - bh
                val color  = if (i == todayIndex) barColor else barColor.copy(alpha = 0.4f)
                val radius = 6.dp.toPx()
                
                val fw = (barWidth - spacing).coerceAtLeast(4f)

                drawRoundRect(
                    color        = color,
                    topLeft      = Offset(x, y),
                    size         = Size(fw, bh),
                    cornerRadius = CornerRadius(radius)
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            weekData.forEachIndexed { i, (label, _) ->
                Text(
                    text  = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color      = if (i == todayIndex) Teal40 else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (i == todayIndex) FontWeight.Bold else FontWeight.Normal
                    )
                )
            }
        }
    }
}

// ─────────────────────────── Pie Chart (Compose-native) ──────────────────────

private data class PieSlice(val label: String, val fraction: Float, val color: Color)

private val pieData = listOf(
    PieSlice("High",     0.35f, Color(0xFF00BCD4)),
    PieSlice("Moderate", 0.45f, Color(0xFF4CAF50)),
    PieSlice("Low",      0.20f, Color(0xFFFF6B6B))
)

@Composable
private fun ActivityDistributionSection(modifier: Modifier = Modifier) {
    var drawn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(300); drawn = true }

    val anims = pieData.mapIndexed { index, slice ->
        animateFloatAsState(
            targetValue   = if (drawn) slice.fraction else 0f,
            animationSpec = tween(1000, easing = FastOutSlowInEasing),
            label         = "pie_$index"
        )
    }

    Column(modifier = modifier) {
        Row(
            modifier             = Modifier.fillMaxWidth(),
            verticalAlignment    = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pie
            Canvas(
                modifier = Modifier
                    .size(110.dp)
            ) {
                var startAngle = -90f
                pieData.forEachIndexed { i, slice ->
                    val sweep = anims[i].value * 360f
                    drawArc(
                        color      = slice.color,
                        startAngle = startAngle,
                        sweepAngle = sweep,
                        useCenter  = false,
                        style      = Stroke(width = size.minDimension * 0.25f, cap = StrokeCap.Butt)
                    )
                    startAngle += sweep
                }
            }

            Spacer(Modifier.width(8.dp))

            // Legend
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pieData.forEach { slice ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .background(slice.color, CircleShape)
                        )
                        Column {
                            Text(
                                slice.label,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                "${(slice.fraction * 100).toInt()}%",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
