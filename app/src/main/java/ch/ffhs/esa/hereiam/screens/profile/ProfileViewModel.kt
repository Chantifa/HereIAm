package ch.ffhs.esa.hereiam.screens.profile

import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.databinding.FragmentProfileBinding
import ch.ffhs.esa.hereiam.services.AuthenticationServiceFirebaseAuthImplementation
import ch.ffhs.esa.hereiam.services.StorageServiceFirebaseImplementation
import ch.ffhs.esa.hereiam.util.toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class ProfileViewModel : ViewModel() {

    val currentUser = MutableLiveData<FirebaseUser>()
    private val authenticationService = AuthenticationServiceFirebaseAuthImplementation()
    private val storageService = StorageServiceFirebaseImplementation()

    init {
        authenticationService.getCurrentUser(currentUser)
    }

    private lateinit var imageUri: Uri
    private lateinit var storageRef: StorageReference

    fun uploadImage(
        bitmap: Bitmap,
        baos: ByteArrayOutputStream
    ): UploadTask {
        val (storageRef, upload) = storageService.uploadImage(bitmap, baos, currentUser.value)
        this.storageRef = storageRef
        return upload
    }

    fun waitOnUpload(
        upload: UploadTask,
        bitmap: Bitmap,
        binding: FragmentProfileBinding,
        context: FragmentActivity?
    ) {
        upload.addOnCompleteListener { uploadTask ->
            try {
                if (uploadTask.isSuccessful) {
                    storageRef.downloadUrl.addOnCompleteListener { urlTask ->
                        urlTask.result?.let {
                            imageUri = it
                            context?.toast(imageUri.toString())
                            binding.profileAvatar.setImageBitmap(bitmap)
                        }
                    }
                } else {
                    uploadTask.exception?.let {
                        context?.toast(it.message!!)
                    }
                }
            } catch (e: Exception) {
                context?.toast(e.message!!)
            }
        }
    }
}