package ch.ffhs.esa.hereiam.model

import com.google.firebase.Timestamp

data class Entry(val heading: String = "", val text: String = "", val timestamp: Timestamp = Timestamp.now()) {
}