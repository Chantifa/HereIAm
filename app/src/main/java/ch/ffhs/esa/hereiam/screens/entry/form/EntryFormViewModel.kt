package ch.ffhs.esa.hereiam.screens.entry.form

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.model.Entry
import ch.ffhs.esa.hereiam.services.DatabaseService
import ch.ffhs.esa.hereiam.services.DatabaseServiceFirestore
import ch.ffhs.esa.hereiam.services.StorageService
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase
import ch.ffhs.esa.hereiam.util.getShortAddress

class EntryFormViewModel : ViewModel() {
    private val databaseService: DatabaseService = DatabaseServiceFirestore()
    private val storageService: StorageService = StorageServiceFirebase()
    val image = MutableLiveData<Bitmap>()
    private val folder = "entryImages"
    private var pathToImage: String? = null
    private val author = HereIAmApplication.currentUser?.displayName
    private val currentLocation = HereIAmApplication.currentLocation
    val currentLocationString = HereIAmApplication.currentLocation.getShortAddress()

    suspend fun addEntry(
        heading: String,
        text: String
    ) {
        val location = currentLocation
        val entry = Entry(
            heading,
            text,
            location.getShortAddress(),
            location?.latitude,
            location?.longitude,
            author,
            pathToImage
        )
        databaseService.addEntry(entry)
    }

    suspend fun uploadImage() {
        image.value?.apply {
            pathToImage = storageService.uploadImageAndSaveRelativePath(this, folder)
        }
    }
}
