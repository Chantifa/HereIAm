package ch.ffhs.esa.hereiam.screens.entry.form

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.screens.home.HomeViewModel
import ch.ffhs.esa.hereiam.services.DatabaseService
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore
import ch.ffhs.esa.hereiam.services.StorageService
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase

class EntryFormViewModel : ViewModel() {
    private val databaseService: DatabaseService = DatabaseServiceFirestore()
    private val storageService: StorageService = StorageServiceFirebase()
    val image = MutableLiveData<Bitmap>()
    val locationName = HomeViewModel.locationName
    private val folder = "entryImages"
    private var pathToImage: String? = null

    suspend fun addEntry(
        heading: String,
        text: String
    ) {
        val author = HereIAmApplication.currentUser?.displayName
        val entry = Entry(heading, text, "LocationName TODO", 0.0, 0.0, author, pathToImage)
        databaseService.addEntry(entry)
    }

    suspend fun uploadImage() {
        image.value?.apply {
            pathToImage = storageService.uploadImageAndSaveRelativePath(this, folder)
        }
    }
}
