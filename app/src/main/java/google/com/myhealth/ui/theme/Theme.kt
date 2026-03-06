package google.com.myhealth.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ShapeDefaults
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary             = Teal40,
    onPrimary           = Color.White,
    primaryContainer    = Color(0xFFB2EBF2),
    onPrimaryContainer  = Color(0xFF001F23),
    secondary           = Emerald40,
    onSecondary         = Color.White,
    secondaryContainer  = Color(0xFFC8E6C9),
    onSecondaryContainer= Color(0xFF0A1F0B),
    tertiary            = Coral40,
    onTertiary          = Color.White,
    tertiaryContainer   = Color(0xFFFFCDD2),
    onTertiaryContainer = Color(0xFF3B0007),
    background          = NeutralLight,
    onBackground        = NeutralGrey80,
    surface             = NeutralCard,
    onSurface           = NeutralGrey80,
    surfaceVariant      = NeutralGrey10,
    onSurfaceVariant    = NeutralGrey50,
    outline             = NeutralGrey20,
    error               = CalorieRed,
    onError             = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary             = Teal80,
    onPrimary           = Color(0xFF003640),
    primaryContainer    = Teal20,
    onPrimaryContainer  = Color(0xFFB2EBF2),
    secondary           = Emerald80,
    onSecondary         = Color(0xFF0A3D0A),
    secondaryContainer  = Emerald20,
    onSecondaryContainer= Color(0xFFC8E6C9),
    tertiary            = Coral80,
    onTertiary          = Color(0xFF4A0010),
    tertiaryContainer   = Coral20,
    onTertiaryContainer = Color(0xFFFFCDD2),
    background          = DarkBackground,
    onBackground        = DarkOnSurface,
    surface             = DarkSurface,
    onSurface           = DarkOnSurface,
    surfaceVariant      = DarkSurface2,
    onSurfaceVariant    = NeutralGrey50,
    outline             = Color(0xFF2D3748),
    error               = Coral80,
    onError             = Color(0xFF4A0010)
)

val HealthShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small      = RoundedCornerShape(12.dp),
    medium     = RoundedCornerShape(16.dp),
    large      = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun MyHealthTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        shapes      = HealthShapes,
        content     = content
    )
}