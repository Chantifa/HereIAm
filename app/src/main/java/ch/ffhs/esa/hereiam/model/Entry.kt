package ch.ffhs.esa.hereiam.model

import com.google.firebase.Timestamp
import java.util.*

data class Entry(
    val entryTitle: String,
    val entryContent: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double
) {
    val entryLastModified = Timestamp.now()
    val entryUUID = generateId()

    companion object {
        private fun generateId(): String {
            return UUID.randomUUID().toString()
        }
    }

}