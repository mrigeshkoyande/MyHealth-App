package google.com.myhealth.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import google.com.myhealth.model.Exercise
import google.com.myhealth.ui.components.AnimatedProgressRing
import google.com.myhealth.ui.components.SectionHeader
import google.com.myhealth.ui.theme.Emerald40
import google.com.myhealth.ui.theme.Teal40
import google.com.myhealth.viewmodel.ExerciseViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(navController: NavController) {
    val vm: ExerciseViewModel = viewModel()
    val exercises by vm.exercises.collectAsState(initial = emptyList())
    val listState = rememberLazyListState()
    val isFabExpanded by remember { derivedStateOf { !listState.isScrollInProgress } }

    var showAddSheet   by remember { mutableStateOf(false) }
    var deleteTarget   by remember { mutableStateOf<Exercise?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // ── Header ────────────────────────────────────────────────────────
            ExerciseHeader(sessionCount = exercises.size)

            if (exercises.isEmpty()) {
                ExerciseEmptyState(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    state       = listState,
                    modifier    = Modifier.weight(1f).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        top = 8.dp, bottom = 96.dp
                    )
                ) {
                    items(exercises, key = { it.id }) { exercise ->
                        ExerciseCard(
                            exercise  = exercise,
                            vm        = vm,
                            onDelete  = { deleteTarget = exercise }
                        )
                    }
                }
            }
        }

        // ── FAB ──────────────────────────────────────────────────────────────
        ExtendedFloatingActionButton(
            onClick    = { showAddSheet = true },
            expanded   = isFabExpanded,
            icon       = { Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White) },
            text       = { Text("Add Workout", color = Color.White, fontWeight = FontWeight.Bold) },
            modifier   = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Teal40,
            contentColor   = Color.White
        )

        // ── Add Sheet ─────────────────────────────────────────────────────────
        if (showAddSheet) {
            AddExerciseBottomSheet(
                onDismiss = { showAddSheet = false },
                onSave    = { name, notes -> vm.addExercise(name, notes); showAddSheet = false }
            )
        }

        // ── Delete Dialog ─────────────────────────────────────────────────────
        deleteTarget?.let { ex ->
            AlertDialog(
                onDismissRequest = { deleteTarget = null },
                title  = { Text("Delete Workout") },
                text   = { Text("Remove \"${ex.name}\" from your workout list?") },
                confirmButton = {
                    Button(
                        onClick = { vm.deleteExercise(ex.id); deleteTarget = null },
                        colors  = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))
                    ) { Text("Delete", color = Color.White) }
                },
                dismissButton = {
                    TextButton(onClick = { deleteTarget = null }) { Text("Cancel") }
                },
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

// ─────────────────────────── Header ──────────────────────────────────────────

@Composable
private fun ExerciseHeader(sessionCount: Int) {
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
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        "Workout Tracker",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = Color.White, fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(
                        "$sessionCount workout${if (sessionCount != 1) "s" else ""} logged",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.75f)
                        )
                    )
                }
            }
        }
    }
}

// ─────────────────────────── Exercise Card ────────────────────────────────────

@Composable
private fun ExerciseCard(
    exercise: Exercise,
    vm: ExerciseViewModel,
    onDelete: () -> Unit
) {
    val running = vm.isRunning(exercise.id)

    // Live elapsed seconds
    var elapsed by remember(exercise.id, running) { mutableIntStateOf(0) }
    
    LaunchedEffect(running, exercise.id) {
        if (running) {
            while (true) {
                delay(1000)
                elapsed++
            }
        }
    }

    val totalSeconds = exercise.totalSeconds + elapsed
    val displayMin   = totalSeconds / 60
    val displaySec   = totalSeconds % 60
    val goalSeconds  = 30 * 60L // 30 min goal
    val progress     = (totalSeconds / goalSeconds.toFloat()).coerceIn(0f, 1f)

    val buttonColor by animateColorAsState(
        targetValue   = if (running) Color(0xFFEF5350) else Emerald40,
        animationSpec = tween(300),
        label         = "btn_color"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp))
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = { onDelete() })
            },
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Progress ring
            AnimatedProgressRing(
                progress      = progress,
                size          = 64.dp,
                strokeWidth   = 6.dp,
                trackColor    = MaterialTheme.colorScheme.surfaceVariant,
                progressColor = if (running) Color(0xFFEF5350) else Teal40,
                centerContent = {
                    Icon(
                        Icons.AutoMirrored.Filled.DirectionsRun,
                        contentDescription = null,
                        tint     = if (running) Color(0xFFEF5350) else Teal40,
                        modifier = Modifier.size(22.dp)
                    )
                }
            )

            // Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    exercise.name,
                    style    = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (exercise.notes.isNotBlank()) {
                    Text(
                        exercise.notes,
                        style    = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.height(6.dp))
                // Duration chip
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = if (running) Color(0xFFEF5350).copy(alpha = 0.12f)
                            else Teal40.copy(alpha = 0.12f)
                ) {
                    Text(
                        if (running)
                            "⏱ $displayMin:${displaySec.toString().padStart(2, '0')}"
                        else
                            "${displayMin}m ${displaySec}s",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color      = if (running) Color(0xFFEF5350) else Teal40,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            // Start / Stop button
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(buttonColor),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { if (running) vm.stopTimer() else vm.startTimer(exercise.id) }
                ) {
                    Icon(
                        if (running) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (running) "Stop" else "Start",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

// ─────────────────────────── Empty State ─────────────────────────────────────

@Composable
private fun ExerciseEmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        Brush.radialGradient(listOf(Teal40.copy(alpha = 0.2f), Color.Transparent)),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.FitnessCenter,
                    contentDescription = null,
                    tint = Teal40,
                    modifier = Modifier.size(48.dp)
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                "No workouts yet",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Tap + to log your first workout session!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

// ─────────────────────────── Add Bottom Sheet ─────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddExerciseBottomSheet(
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var name  by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var targetMinutes by remember { mutableFloatStateOf(30f) }

    ModalBottomSheet(
        onDismissRequest  = onDismiss,
        sheetState        = sheetState,
        shape             = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor    = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 32.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "New Workout",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextField(
                value         = name,
                onValueChange = { name = it },
                label         = { Text("Exercise Name") },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                singleLine    = true
            )
            OutlinedTextField(
                value         = notes,
                onValueChange = { notes = it },
                label         = { Text("Notes (optional)") },
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(14.dp),
                maxLines      = 2
            )
            Column {
                Text(
                    "Target: ${targetMinutes.toInt()} min",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Slider(
                    value         = targetMinutes,
                    onValueChange = { targetMinutes = it },
                    valueRange    = 5f..120f,
                    steps         = 22,
                    modifier      = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick  = { if (name.isNotBlank()) onSave(name, notes) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape    = RoundedCornerShape(14.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = Teal40)
            ) {
                Text(
                    "Save Workout",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold, color = Color.White
                    )
                )
            }
        }
    }
}