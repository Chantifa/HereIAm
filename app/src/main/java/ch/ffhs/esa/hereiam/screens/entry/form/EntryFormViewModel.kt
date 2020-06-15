package ch.ffhs.esa.hereiam.screens.entry.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestoreImplementation

class EntryFormViewModel : ViewModel() {
    private val databaseService = DatabaseServiceFirestoreImplementation()

    fun addEntry(
        heading: String,
        text: String
    ) {
        databaseService.addEntry(heading, text)
    }
}