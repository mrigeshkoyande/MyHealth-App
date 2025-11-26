package google.com.myhealth.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import google.com.myhealth.data.ExerciseRepository
import google.com.myhealth.model.Exercise
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = ExerciseRepository(application.applicationContext)

    private val _exercises = MutableStateFlow<List<Exercise>>(repo.getAll())
    val exercises: StateFlow<List<Exercise>> = _exercises

    private var runningId: String? = null
    private var runningStart: Long = 0L

    fun refresh() {
        _exercises.value = repo.getAll()
    }

    fun addExercise(name: String, notes: String) {
        viewModelScope.launch {
            repo.add(name, notes)
            refresh()
        }
    }

    fun updateExercise(e: Exercise) {
        viewModelScope.launch {
            repo.update(e)
            refresh()
        }
    }

    fun deleteExercise(id: String) {
        viewModelScope.launch {
            repo.delete(id)
            refresh()
        }
    }

    fun startTimer(id: String) {
        if (runningId != null) return
        runningId = id
        runningStart = System.currentTimeMillis()
    }

    fun stopTimer() {
        val id = runningId ?: return
        val now = System.currentTimeMillis()
        val elapsedSec = (now - runningStart) / 1000
        runningId = null
        runningStart = 0L
        viewModelScope.launch {
            val list = repo.getAll().toMutableList()
            val idx = list.indexOfFirst { it.id == id }
            if (idx >= 0) {
                val e = list[idx]
                val updated = e.copy(totalSeconds = e.totalSeconds + elapsedSec)
                repo.update(updated)
                refresh()
            }
        }
    }

    fun isRunning(id: String): Boolean = runningId == id
}
