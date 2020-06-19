package ch.ffhs.esa.hereiam.screens.entry.form

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.screens.home.HomeViewModel
import ch.ffhs.esa.hereiam.services.DatabaseService
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore

class EntryFormViewModel : ViewModel() {
    private val databaseService: DatabaseService = DatabaseServiceFirestore()
    val image = MutableLiveData<Bitmap>()
    val locationName = HomeViewModel.locationName

    suspend fun addEntry(
        heading: String,
        text: String
    ) {
        val entry = Entry(heading, text, "LocationName TODO", 0.0, 0.0)
        databaseService.addEntry(entry)
    }
}
