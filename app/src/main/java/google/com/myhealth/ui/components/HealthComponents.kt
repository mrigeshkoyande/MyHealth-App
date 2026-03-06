package google.com.myhealth.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import google.com.myhealth.ui.theme.Teal40
import google.com.myhealth.ui.theme.Teal80
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.foundation.Canvas

// ─── AnimatedProgressRing ────────────────────────────────────────────────────

@Composable
fun AnimatedProgressRing(
    progress: Float,          // 0f..1f
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
    strokeWidth: Dp = 12.dp,
    trackColor: Color = Color.White.copy(alpha = 0.2f),
    progressColor: Color = Color.White,
    centerContent: @Composable (() -> Unit)? = null
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        label = "ring_progress"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val strokePx = strokeWidth.toPx()
            val diameter = this.size.minDimension - strokePx
            val topLeft   = androidx.compose.ui.geometry.Offset(strokePx / 2f, strokePx / 2f)
            val arcSize   = androidx.compose.ui.geometry.Size(diameter, diameter)

            // Track
            drawArc(
                color      = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter  = false,
                topLeft    = topLeft,
                size       = arcSize,
                style      = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
            // Progress
            drawArc(
                color      = progressColor,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter  = false,
                topLeft    = topLeft,
                size       = arcSize,
                style      = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }
        centerContent?.invoke()
    }
}

// ─── StatCard ────────────────────────────────────────────────────────────────

@Composable
fun StatCard(
    title: String,
    value: String,
    unit: String,
    icon: ImageVector,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier
) {
    var animated by remember { mutableStateOf(false) }
    val animatable = remember { Animatable(0f) }
    
    // Only animate on initial load
    LaunchedEffect(title) {
        if (!animated) {
            animatable.animateTo(1f, animationSpec = tween(800, easing = FastOutSlowInEasing))
            animated = true
        }
    }

    Card(
        modifier = modifier
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(gradientColors))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector       = icon,
                        contentDescription = title,
                        tint              = Color.White,
                        modifier          = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text  = value,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color      = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize   = 22.sp
                    )
                )
                Text(
                    text  = unit,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.White.copy(alpha = 0.8f)
                    )
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text  = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color.White.copy(alpha = 0.7f)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// ─── MacroCard ───────────────────────────────────────────────────────────────

@Composable
fun MacroCard(
    label: String,
    current: Float,
    target: Float,
    unit: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    val progress = (current / target).coerceIn(0f, 1f)

    Card(
        modifier = modifier.shadow(6.dp, RoundedCornerShape(20.dp)),
        shape    = RoundedCornerShape(20.dp),
        colors   = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedProgressRing(
                progress      = progress,
                size          = 72.dp,
                strokeWidth   = 7.dp,
                trackColor    = color.copy(alpha = 0.15f),
                progressColor = color,
                centerContent = {
                    Text(
                        text  = "${current.toInt()}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color      = color,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text  = label,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text  = "/ ${target.toInt()}$unit",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

// ─── SectionHeader ───────────────────────────────────────────────────────────

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier            = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment   = Alignment.CenterVertically
    ) {
        Text(
            text  = title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        if (actionLabel != null && onAction != null) {
            Text(
                text  = actionLabel,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

// ─── MotivationalBanner ──────────────────────────────────────────────────────

private val motivationalQuotes = listOf(
    "💪 Push harder than yesterday!",
    "🔥 Every rep counts. Keep going!",
    "🏃 Your only limit is you.",
    "⚡ Stronger every day.",
    "🌟 Great things take time."
)

@Composable
fun MotivationalBanner(
    modifier: Modifier = Modifier,
    gradientColors: List<Color> = listOf(
        Color(0xFF00BCD4), Color(0xFF006978)
    )
) {
    val quote = remember { motivationalQuotes.random() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.horizontalGradient(gradientColors))
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text  = quote,
            style = MaterialTheme.typography.titleMedium.copy(
                color      = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

// ─── GlassCard ───────────────────────────────────────────────────────────────
// A subtle semi-transparent surface card for information grouping.

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier  = modifier.shadow(8.dp, RoundedCornerShape(24.dp)),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        content   = { content() }
    )
}
