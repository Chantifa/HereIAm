package ch.ffhs.esa.hereiam.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.graphics.drawable.toDrawable
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_entry_form.*
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
    constructor() : this("", "", "", 0.0, 0.0)
}