package ch.ffhs.esa.hereiam.screens.entry.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.FirebaseFirestore

class EntryFormViewModel : ViewModel() {
    fun addEntry(
        heading: String,
        text: String
    ) {
        FirebaseFirestore.addEntry(heading, text)
    }
}