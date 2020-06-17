package ch.ffhs.esa.hereiam.screens.entry.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.DatabaseService

class EntryFormViewModel : ViewModel() {
    fun addEntry(
        heading: String,
        text: String
    ) {
        DatabaseService.addEntry(heading, text)
    }
}
