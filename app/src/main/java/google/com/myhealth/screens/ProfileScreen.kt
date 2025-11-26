package google.com.myhealth.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.LinearProgressIndicator as LinearProgressIndicator1
import androidx.compose.ui.graphics.vector.ImageVector as ImageVector1

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        // Profile Header
        ProfileHeader()

        Spacer(modifier = Modifier.height(24.dp))

        // Health Status Section
        Text(
            text = "Health Status",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Health Indicators
        HealthIndicatorCard(
            title = "Heart Rate",
            value = "72 bpm",
            status = HealthStatus.GOOD,
            icon = Icons.Default.Favorite
        )

        Spacer(modifier = Modifier.height(16.dp))

        HealthIndicatorCard(
            title = "Blood Pressure",
            value = "128/85",
            status = HealthStatus.MEDIUM,
            icon = Icons.Default.Timeline
        )

        Spacer(modifier = Modifier.height(16.dp))

        HealthIndicatorCard(
            title = "Sleep Quality",
            value = "5.5 hrs",
            status = HealthStatus.BAD,
            icon = Icons.Default.Bedtime
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Personal Information Section
        PersonalInfoSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Goals Section
        GoalsSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Settings Button
        Button(
            onClick = { /* Handle settings */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Settings, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Settings")
        }
    }
}

@Composable
private fun ProfileHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Active Member",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun PersonalInfoSection() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            
            InfoRow("Age", "28 years")
            InfoRow("Height", "175 cm")
            InfoRow("Weight", "70 kg")
            InfoRow("BMI", "22.9")
        }
    }
}

@Composable
private fun GoalsSection() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                    text = "Fitness Goals",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            
            GoalItem(
                icon = Icons.AutoMirrored.Filled.DirectionsRun,
                title = "Daily Steps",
                goal = "10,000 steps",
                progress = 0.7f
            )
            GoalItem(
                icon = Icons.Default.FitnessCenter,
                title = "Weekly Workouts",
                goal = "4 sessions",
                progress = 0.5f
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun GoalItem(
    icon: ImageVector1,
    title: String,
    goal: String,
    progress: Float
) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = goal,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator1(
            progress = { progress },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

enum class HealthStatus(val color: Color, val label: String) {
    GOOD(Color(0xFF4CAF50), "Good"),
    MEDIUM(Color(0xFFFFA726), "Moderate"),
    BAD(Color(0xFFE57373), "Need Attention")
}

@Composable
private fun HealthIndicatorCard(
    title: String,
    value: String,
    status: HealthStatus,
    icon: ImageVector1
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = status.color
                )
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            StatusIndicator(status)
        }
    }
}

@Composable
private fun StatusIndicator(status: HealthStatus) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(status.color)
        )
        Text(
            text = status.label,
            style = MaterialTheme.typography.bodyMedium,
            color = status.color
        )
    }
}