package ch.ffhs.esa.hereiam.screens.entry.form

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.screens.home.HomeViewModel
import ch.ffhs.esa.hereiam.services.DatabaseService
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class EntryFormViewModel : ViewModel() {
    private val databaseService: DatabaseService = DatabaseServiceFirestore()

    val locationName = HomeViewModel.locationName

    fun addEntry(
        heading: String,
        text: String
    ) {
        val entry = Entry(heading, text, "LocationName TODO", 0.0, 0.0)
        CoroutineScope(IO).launch {
            try {
                databaseService.addEntry(entry)
                Timber.e("Entry added successfully!")
            } catch (e: Exception) {
                Timber.e("Error while saving Entry: ${e.message}")
            }
        }
    }
}
