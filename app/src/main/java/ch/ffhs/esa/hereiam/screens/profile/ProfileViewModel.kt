package ch.ffhs.esa.hereiam.screens.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.HereIAmApplication
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase

class ProfileViewModel : ViewModel() {

    private val userPicture = MutableLiveData<Uri>()
    private val storageService = StorageServiceFirebase()
    private val folder = "profileImages"

    fun uploadImage(bitmap: Bitmap) {
        val userId = HereIAmApplication.currentUser.value?.uid
        storageService.uploadImage(bitmap, folder, userId ?: "")
            .observeForever { result -> userPicture.value = result }
    }

}