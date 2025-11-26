package google.com.myhealth.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import google.com.myhealth.model.Exercise
import google.com.myhealth.viewmodel.ExerciseViewModel

@Composable
fun ExerciseScreen(navController: NavController) {
    val vm: ExerciseViewModel = viewModel()
    val exercises by vm.exercises.collectAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Exercises", style = MaterialTheme.typography.headlineSmall)
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(exercises, key = { it.id }) { e ->
                ExerciseRow(e, vm)
            }
        }

        var showAdd by remember { mutableStateOf(false) }
        if (showAdd) AddExerciseDialog(onAdd = { name, notes -> vm.addExercise(name, notes); showAdd = false }, onDismiss = { showAdd = false })

        Box(modifier = Modifier.fillMaxWidth()) {
            FloatingActionButton(onClick = { showAdd = true }, modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
private fun ExerciseRow(e: Exercise, vm: ExerciseViewModel) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.fillMaxWidth(0.72f)) {
            Text(e.name, style = MaterialTheme.typography.titleMedium)
            Text("Duration: ${e.totalSeconds / 60} min", style = MaterialTheme.typography.bodySmall)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val running = vm.isRunning(e.id)
            Button(onClick = { if (running) vm.stopTimer() else vm.startTimer(e.id) }) {
                Text(if (running) "Stop" else "Start")
            }
            TextButton(onClick = { vm.deleteExercise(e.id) }) { Text("Delete") }
        }
    }
}

@Composable
private fun AddExerciseDialog(onAdd: (String, String) -> Unit, onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Exercise") },
        text = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes") }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            TextButton(onClick = { if (name.isNotBlank()) onAdd(name, notes) }) { Text("Add") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
 