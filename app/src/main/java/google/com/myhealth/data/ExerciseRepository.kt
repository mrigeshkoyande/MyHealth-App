package google.com.myhealth.data

import android.content.Context
import google.com.myhealth.model.Exercise
import java.util.UUID

class ExerciseRepository(private val context: Context) {
    private val prefs = context.getSharedPreferences("exercises_prefs", Context.MODE_PRIVATE)
    private val KEY = "exercises_set"

    private fun serialize(e: Exercise): String = listOf(e.id, e.name, e.notes.replace("|", " "), e.totalSeconds.toString()).joinToString("|")

    private fun deserialize(s: String): Exercise? {
        val parts = s.split("|")
        if (parts.size < 4) return null
        return Exercise(parts[0], parts[1], parts[2], parts[3].toLongOrNull() ?: 0L)
    }

    fun getAll(): List<Exercise> {
        val set = prefs.getStringSet(KEY, emptySet()) ?: emptySet()
        return set.mapNotNull { deserialize(it) }.sortedBy { it.name }
    }

    fun saveAll(list: List<Exercise>) {
        val set = list.map { serialize(it) }.toSet()
        prefs.edit().putStringSet(KEY, set).apply()
    }

    fun add(name: String, notes: String = ""): Exercise {
        val e = Exercise(UUID.randomUUID().toString(), name, notes, 0L)
        val current = getAll().toMutableList()
        current.add(e)
        saveAll(current)
        return e
    }

    fun update(updated: Exercise) {
        val current = getAll().toMutableList()
        val idx = current.indexOfFirst { it.id == updated.id }
        if (idx >= 0) current[idx] = updated
        saveAll(current)
    }

    fun delete(id: String) {
        val current = getAll().filterNot { it.id == id }
        saveAll(current)
    }
}
