package ch.ffhs.esa.hereiam.screens.entry.form

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.DatabaseService

class EntryFormViewModel: ViewModel() {


    fun addEntry(
        photo: Bitmap,
        heading: String,
        text: String
    ) {
        DatabaseService.addEntry(photo, heading, text)
    }

    fun deleteEntry(
        photo: Bitmap,
        heading: String,
        text: String
    ){
        DatabaseService.deleteEntry(photo, heading, text)
    }
}