package ch.ffhs.esa.hereiam.model

import com.google.firebase.Timestamp
import java.util.*

data class Entry(


    val entryTitle: String,
    val entryContent: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val entryLastModified: Timestamp = Timestamp.now(),
    val entryUUID: String = UUID.randomUUID().toString()
) {


    // Firestore needs an empty constructor for deserialization
    constructor(heading: String, text: String) : this("", "", "",0.0,0.0)
}