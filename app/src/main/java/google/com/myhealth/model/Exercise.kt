package google.com.myhealth.model

data class Exercise(
    val id: String,
    val name: String,
    val notes: String = "",
    val totalSeconds: Long = 0L
)
