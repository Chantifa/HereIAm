package ch.ffhs.esa.hereiam.model

import com.google.firebase.Timestamp

data class Entry(
    val entryTitle: String,
    val entryContent: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double
) {
    val entryLastModified: Timestamp = Timestamp.now()
}