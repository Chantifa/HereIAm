package ch.ffhs.esa.hereiam.screens.profile

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.services.StorageService
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {

    private val storageService: StorageService = StorageServiceFirebase()
    private val folder = "profileImages"

    suspend fun uploadImage(bitmap: Bitmap) {
        val userId = HereIAmApplication.currentUser.value?.uid
        val path = storageService.uploadImageAndSaveUri(bitmap, folder, userId ?: "")
        val changeRequest = UserProfileChangeRequest.Builder().setPhotoUri(path).build()
        HereIAmApplication.currentUser.value?.updateProfile(changeRequest)?.await()
    }

    suspend fun saveUserName(name: String) {
        val changeRequest = UserProfileChangeRequest.Builder().setDisplayName(name).build()
        HereIAmApplication.currentUser.value?.updateProfile(changeRequest)?.await()
    }
}