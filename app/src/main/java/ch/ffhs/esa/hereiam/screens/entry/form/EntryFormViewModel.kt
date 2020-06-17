package ch.ffhs.esa.hereiam.screens.entry.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore

class EntryFormViewModel : ViewModel() {
    private val databaseService = DatabaseServiceFirestore()

    fun addEntry(
        heading: String,
        text: String
    ) {
        databaseService.addEntry(heading, text)
    }
}
