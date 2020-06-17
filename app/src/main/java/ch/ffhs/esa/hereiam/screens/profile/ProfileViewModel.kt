package ch.ffhs.esa.hereiam.screens.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuth
import ch.ffhs.esa.hereiam.services.StorageServiceFirebase
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel : ViewModel() {

    val currentUser = MutableLiveData<FirebaseUser>()
    private val userPicture = MutableLiveData<Uri>()
    private val authenticationService = AuthenticationServiceFirebaseAuth()
    private val storageService = StorageServiceFirebase()
    private val folder = "profileImages"

    init {
        authenticationService.getCurrentUser()
            .observeForever { result -> currentUser.value = result }
    }

    fun uploadImage(bitmap: Bitmap) {
        val userId = currentUser.value?.uid
        storageService.uploadImage(bitmap, folder, userId ?: "")
            .observeForever { result -> userPicture.value = result }
    }

}