package ch.ffhs.esa.hereiam.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

const val pattern = "d. MMMM yyyy, kk.mm"

data class Entry(
    val entryTitle: String,
    val entryContent: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val author: String?,
    val imagePath: String?,
    val entryLastModified: Timestamp = Timestamp.now(),
    val entryUUID: String = UUID.randomUUID().toString()
) {
    fun getMeta(): String {
        var str = ""
        author?.let {
            str += "$author, "
        }
        str += SimpleDateFormat(pattern, Locale.GERMAN).format(entryLastModified.toDate()) + " Uhr"
        return str
    }

    // Firestore needs an empty constructor for deserialization
    constructor() : this("", "", "", 0.0, 0.0, null, null)
}
